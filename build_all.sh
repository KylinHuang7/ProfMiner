#!/bin/bash
# ============================================================
# ProfMiner 多版本构建与发布脚本
# 功能：
#   1. 编译所有分支的 jar 包（无需手动切换分支）
#   2. 可选推送到 CurseForge
#
# 用法：
#   ./build_all.sh              # 仅编译所有版本
#   ./build_all.sh --publish    # 编译并发布到 CurseForge
#   ./build_all.sh --branch 1.20.1  # 仅编译指定分支
#
# 环境变量：
#   CURSEFORGE_API_KEY  - CurseForge API Token（发布时必需）
#   CURSEFORGE_PROJECT_ID - CurseForge 项目 ID（发布时必需）
# ============================================================

set -e

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 项目根目录
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
OUTPUT_DIR="${PROJECT_DIR}/build/release"

# 默认配置
PUBLISH=false
TARGET_BRANCH=""
CURSEFORGE_PROJECT_ID="${CURSEFORGE_PROJECT_ID:-}"
CURSEFORGE_API_KEY="${CURSEFORGE_API_KEY:-}"

# 分支配置：分支名 -> (MC版本, 加载器, 游戏版本ID)
# CurseForge 游戏版本 ID 可在 https://minecraft.curseforge.com/api/game/versions 查询
declare -A BRANCH_CONFIG
BRANCH_CONFIG["master"]="1.21.1|neoforge|NeoForge"
BRANCH_CONFIG["1.20.1"]="1.20.1|forge|Forge"

# CurseForge MC 版本映射
declare -A CF_MC_VERSIONS
CF_MC_VERSIONS["1.21.1"]="1.21.1"
CF_MC_VERSIONS["1.20.1"]="1.20.1"

# ============================================================
# 工具函数
# ============================================================

log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

usage() {
    echo "用法: $0 [选项]"
    echo ""
    echo "选项:"
    echo "  --publish              编译后发布到 CurseForge"
    echo "  --branch <分支名>     仅编译指定分支 (如: master, 1.20.1)"
    echo "  --list                 列出所有可用分支"
    echo "  --help                 显示帮助信息"
    echo ""
    echo "环境变量:"
    echo "  CURSEFORGE_API_KEY     CurseForge API Token"
    echo "  CURSEFORGE_PROJECT_ID  CurseForge 项目 ID"
    echo ""
    echo "示例:"
    echo "  $0                         # 编译所有版本"
    echo "  $0 --branch master         # 仅编译 master 分支"
    echo "  $0 --publish               # 编译并发布所有版本"
}

# 解析命令行参数
parse_args() {
    while [[ $# -gt 0 ]]; do
        case $1 in
            --publish)
                PUBLISH=true
                shift
                ;;
            --branch)
                TARGET_BRANCH="$2"
                shift 2
                ;;
            --list)
                echo "可用分支:"
                for branch in "${!BRANCH_CONFIG[@]}"; do
                    IFS='|' read -r mc_ver loader _ <<< "${BRANCH_CONFIG[$branch]}"
                    echo "  ${branch} -> MC ${mc_ver} (${loader})"
                done
                exit 0
                ;;
            --help|-h)
                usage
                exit 0
                ;;
            *)
                log_error "未知参数: $1"
                usage
                exit 1
                ;;
        esac
    done
}

# 检查发布所需的环境变量
check_publish_env() {
    if [ "$PUBLISH" = true ]; then
        if [ -z "$CURSEFORGE_API_KEY" ]; then
            log_error "发布需要设置 CURSEFORGE_API_KEY 环境变量"
            log_info "获取方式: https://www.curseforge.com/account/api-tokens"
            exit 1
        fi
        if [ -z "$CURSEFORGE_PROJECT_ID" ]; then
            log_error "发布需要设置 CURSEFORGE_PROJECT_ID 环境变量"
            exit 1
        fi
    fi
}

# 获取当前分支名
get_current_branch() {
    git -C "$PROJECT_DIR" rev-parse --abbrev-ref HEAD
}

# 编译指定分支
build_branch() {
    local branch=$1
    local config="${BRANCH_CONFIG[$branch]}"

    if [ -z "$config" ]; then
        log_error "未知分支: $branch"
        return 1
    fi

    IFS='|' read -r mc_ver loader loader_name <<< "$config"

    log_info "=========================================="
    log_info "编译分支: ${branch}"
    log_info "MC 版本: ${mc_ver} | 加载器: ${loader_name}"
    log_info "=========================================="

    local current_branch
    current_branch=$(get_current_branch)

    # 如果不在目标分支，先切换
    if [ "$current_branch" != "$branch" ]; then
        log_info "切换到分支: ${branch}"
        git -C "$PROJECT_DIR" checkout "$branch" --quiet
    fi

    # 执行 Gradle 构建
    log_info "开始 Gradle 构建..."
    cd "$PROJECT_DIR"

    if [ "$loader" = "neoforge" ]; then
        ./gradlew :neoforge:build --no-daemon -q
    elif [ "$loader" = "forge" ]; then
        ./gradlew :forge:build --no-daemon -q
    fi

    # 复制产物到输出目录
    local output_subdir="${OUTPUT_DIR}/${mc_ver}-${loader}"
    mkdir -p "$output_subdir"

    local jar_dir="${PROJECT_DIR}/${loader}/build/libs"
    local jar_file
    jar_file=$(find "$jar_dir" -name "*.jar" ! -name "*-dev*" ! -name "*-shadow*" ! -name "*-sources*" | head -1)

    if [ -n "$jar_file" ] && [ -f "$jar_file" ]; then
        local dest_name="profminer-${mc_ver}-${loader}-$(basename "$jar_file" | grep -oP '\d+\.\d+\.\d+' | head -1).jar"
        if [ -z "$dest_name" ] || [ "$dest_name" = "profminer-${mc_ver}-${loader}-.jar" ]; then
            dest_name="profminer-${mc_ver}-${loader}.jar"
        fi
        cp "$jar_file" "${output_subdir}/${dest_name}"
        log_success "产物: ${output_subdir}/${dest_name}"
    else
        log_warn "未找到编译产物，检查 ${jar_dir}"
    fi

    # 切回原分支
    if [ "$current_branch" != "$branch" ]; then
        git -C "$PROJECT_DIR" checkout "$current_branch" --quiet
    fi
}

# 发布到 CurseForge
publish_to_curseforge() {
    local branch=$1
    local config="${BRANCH_CONFIG[$branch]}"

    IFS='|' read -r mc_ver loader loader_name <<< "$config"

    local output_subdir="${OUTPUT_DIR}/${mc_ver}-${loader}"
    local jar_file
    jar_file=$(find "$output_subdir" -name "*.jar" | head -1)

    if [ -z "$jar_file" ] || [ ! -f "$jar_file" ]; then
        log_error "未找到 ${branch} 的编译产物，跳过发布"
        return 1
    fi

    log_info "发布到 CurseForge: $(basename "$jar_file")"
    log_info "  MC 版本: ${mc_ver}"
    log_info "  加载器: ${loader_name}"

    # 构建 CurseForge metadata
    local cf_mc_version="${CF_MC_VERSIONS[$mc_ver]}"
    local release_type="release"

    # 构建 relations（依赖）
    local relations=""
    if [ "$loader" = "neoforge" ]; then
        relations='"relations": {"projects": [{"slug": "architectury-api", "type": "requiredDependency"}]}'
    elif [ "$loader" = "forge" ]; then
        relations='"relations": {"projects": [{"slug": "architectury-api", "type": "requiredDependency"}]}'
    fi

    # 使用 CurseForge API 上传
    local metadata
    metadata=$(cat <<EOF
{
    "changelog": "多版本支持更新，详见 GitHub CHANGELOG.md",
    "changelogType": "text",
    "displayName": "ProfMiner ${mc_ver} (${loader_name})",
    "gameVersions": ["${cf_mc_version}"],
    "releaseType": "${release_type}",
    ${relations}
}
EOF
)

    local response
    response=$(curl -s -w "\n%{http_code}" \
        -X POST "https://minecraft.curseforge.com/api/projects/${CURSEFORGE_PROJECT_ID}/upload-file" \
        -H "X-Api-Token: ${CURSEFORGE_API_KEY}" \
        -F "metadata=${metadata}" \
        -F "file=@${jar_file}")

    local http_code
    http_code=$(echo "$response" | tail -1)
    local body
    body=$(echo "$response" | sed '$d')

    if [ "$http_code" = "200" ]; then
        log_success "发布成功！文件 ID: $(echo "$body" | grep -oP '"id":\s*\K\d+')"
    else
        log_error "发布失败 (HTTP ${http_code}): ${body}"
        return 1
    fi
}

# ============================================================
# 主流程
# ============================================================

main() {
    parse_args "$@"
    check_publish_env

    log_info "ProfMiner 多版本构建脚本"
    log_info "项目目录: ${PROJECT_DIR}"
    echo ""

    # 创建输出目录
    mkdir -p "$OUTPUT_DIR"

    # 记录当前分支，结束后恢复
    local original_branch
    original_branch=$(get_current_branch)

    # 确定要编译的分支列表
    local branches_to_build=()
    if [ -n "$TARGET_BRANCH" ]; then
        if [ -z "${BRANCH_CONFIG[$TARGET_BRANCH]}" ]; then
            log_error "未知分支: $TARGET_BRANCH"
            log_info "可用分支: ${!BRANCH_CONFIG[*]}"
            exit 1
        fi
        branches_to_build=("$TARGET_BRANCH")
    else
        branches_to_build=("${!BRANCH_CONFIG[@]}")
    fi

    # 编译
    local build_success=true
    for branch in "${branches_to_build[@]}"; do
        if ! build_branch "$branch"; then
            log_error "分支 ${branch} 编译失败"
            build_success=false
        fi
        echo ""
    done

    # 恢复原始分支
    local current
    current=$(get_current_branch)
    if [ "$current" != "$original_branch" ]; then
        git -C "$PROJECT_DIR" checkout "$original_branch" --quiet
    fi

    # 发布
    if [ "$PUBLISH" = true ] && [ "$build_success" = true ]; then
        echo ""
        log_info "=========================================="
        log_info "开始发布到 CurseForge"
        log_info "=========================================="
        for branch in "${branches_to_build[@]}"; do
            publish_to_curseforge "$branch"
            echo ""
        done
    fi

    # 总结
    echo ""
    log_info "=========================================="
    log_success "构建完成！"
    log_info "输出目录: ${OUTPUT_DIR}"
    log_info "=========================================="
    echo ""
    ls -la "$OUTPUT_DIR"/*/ 2>/dev/null || true
}

main "$@"
