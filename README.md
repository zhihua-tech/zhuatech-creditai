# CreditAI：企业信用风险与额度决策社区版


## 本次升级：担保折扣与授信到期敞口

对有效担保打折计入无担保基础额度，合并当前敞口和未记账订单，独立检查授信及担保到期。不是授信批准或客户信用评分。

新增已认证的结构化录入工作台（`/operations.html`），支持批量核算、逐条结果依据、分级复核和CSV/完整JSON导出。结果由真实后端算法计算，不是预置演示数字。所有数值精确传递，不调用外部模型。

这是**只读核算**：不写库、不审批、不自动执行财务、签署、销毁或设备控制。输入条件满足不是执行授权。[算法、输入边界与接口说明](docs/OPERATIONS-REVIEW.md)。
## 本次功能更新：客户敞口与额度余量核算

汇总未收应收、未开票订单和有权抵扣的担保金额，计算额度占用与可用余量，拒绝重复单据，不自动授信。 登录后从「业务计算工具」使用，支持校验与完整结果导出。详见[算法口径、接口和测试说明](docs/AI_OPERATIONS_UPGRADE_20261023.md)。

[简体中文](README.md) | [English](README.en.md)

> 出品方：[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)　Java 包名：`cn.zhuatech.creditai`

CreditAI 为财务、销售运营和信用管理团队提供 B2B 客户信用评估工作台。系统综合年度营收、当前应收、最长逾期、延迟付款次数、经营年限与外部风险，输出风险等级、建议可用额度、账期策略和完整原因。

![企业信用风险控制中心](docs/images/creditai-admin.png)

## 决策原则

CreditAI 不直接批准授信。高风险客户、大额额度和异常账期始终进入授权人员审批；社区版规则完全本地运行，不依赖外部 API Key，也不包含任何真实征信数据。

```text
营收 / 应收 / 逾期 / 付款历史 / 外部风险
                    ↓
              可解释风险评分
                    ↓
        建议额度 + 账期策略 + 人工审批
```

核心接口：`POST /api/ai/credit/assess`。

![信用分析师 H5 工作台](docs/images/creditai-h5.png)

## 工程能力

- 客户准入、年度复评、额度调整和风险冻结工作流
- `LOW / MEDIUM / HIGH` 风险分层
- 标准账期、人工复核、现金预付三类建议
- 客户组合、应收账龄、复评日历、额度审批与风险分析
- Java 21、Spring Boot 4、MySQL 8、Vue 3、Docker Compose
- JUnit、MockMvc、H2、JWT、Flyway 和接口文档

## 启动前端演示

```bash
cd frontend
npm install
npm run dev:demo
```

访问 `http://localhost:5173`；管理端 `planner / Demo@2026`，分析师端 `operator / Demo@2026`。演示企业、营收、应收和额度均为虚构信息。

文档入口：[API](docs/api.md) · [系统架构](docs/architecture.md) · [数据设计](docs/database.md) · [部署](deploy/README.md)

## 许可与联系

本工程仅能用于个人、非商业学习交流，**不得商用**。企业内部使用、生产部署、SaaS、交付、收费服务、品牌替换和商业发行，须取得上海如静知华信息科技有限公司书面授权，以 [LICENSE](LICENSE) 为准。

信用管理、应收风控、ERP/CRM 集成、AI 私有化与软件项目外包，请访问[知华科技官网](https://www.zhuatech.cn/)或扫码咨询：

| 技术与方案 | 授权与定制 |
| --- | --- |
| ![微信咨询一](docs/images/zhuatech-wechat-consulting.png) | ![微信咨询二](docs/images/zhuatech-wechat-consulting-2.png) |

搜索关键词：企业信用管理、客户信用评分、授信额度、应收账款风控、Java AI 源码、知华科技。


## 2026 企业级热度项目升级

本次根据公开仓库访问热度补充 **信用额度政策与双人审批**：综合风险评分、逾期、营收、敞口、抵押物和销售例外计算政策额度、可用额度与审批路线。

- 企业 API：`POST /api/enterprise/credit/limit-governance`
- 决策输出：执行许可、量化指标、阻断/升级路线、控制清单
- 可审计性：规则确定、输入输出可留痕，并附正常与阻断场景测试
- AI 接入：预留 DeepSeek-compatible 建议层配置，AI 不直接绕过审批或改变正式业务状态

详细设计见 [企业级升级说明](docs/ENTERPRISE_UPGRADE.md)。深度开发、企业部署和系统集成请联系[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)。
