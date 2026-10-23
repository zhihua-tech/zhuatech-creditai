/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.creditai.workbench;

import static cn.zhuatech.creditai.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "limit", "eligibleCollateral", "documents");
    var limit = money(r, "limit");
    var collateral = money(r, "eligibleCollateral");
    Set<String> ids = new HashSet<>();
    BigDecimal gross = BigDecimal.ZERO;
    List<Map<String, Object>> out = new ArrayList<>();
    for (var d : rows(r, "documents", 0, 500)) {
      keys(d, "id", "type", "amount", "settled");
      String id = id(d, "id");
      unique(ids, id);
      String type = text(d, "type", 20);
      if (!Set.of("RECEIVABLE", "OPEN_ORDER").contains(type))
        throw new IllegalArgumentException("敞口单据类型错误");
      var amount = money(d, "amount");
      var settled = money(d, "settled");
      if (settled.compareTo(amount) > 0) throw new IllegalArgumentException("已结金额不能超过原金额");
      var remaining = amount.subtract(settled);
      gross = gross.add(remaining);
      out.add(Map.of("id", id, "type", type, "remaining", remaining));
    }
    var net = gross.subtract(collateral).max(BigDecimal.ZERO);
    var headroom = limit.subtract(net);
    return Map.of(
        "grossExposure",
        gross,
        "netExposure",
        net,
        "headroom",
        headroom,
        "overLimit",
        headroom.signum() < 0,
        "utilizationDefined",
        limit.signum() > 0,
        "utilizationPercent",
        limit.signum() == 0
            ? BigDecimal.ZERO
            : net.multiply(new BigDecimal("100"))
                .divide(limit, 6, RoundingMode.HALF_UP)
                .stripTrailingZeros(),
        "documents",
        out,
        "automaticCreditDecision",
        false);
  }
}
