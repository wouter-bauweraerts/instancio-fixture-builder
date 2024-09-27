# 项目模块划分：
项目分为 api、app、framework 三个子模块。

api：提供给外部调用的模块
app：应用的业务处理模块
framework：应用的框架处理模块

# 环境设置：
## 运行环境
jdk：1.8
maven：3.8.6
## 项目环境
使用 maven profile 定义各个环境
local：本地开发运行环境
dev：开发联调环境
sit：测试环境
prd：生产环境

# 启动方式：
在 app 模块中运行 Application 类。
