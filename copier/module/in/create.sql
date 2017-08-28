<#assign prefix=""/>
<#list packages as pkg>
  <#if (pkg_index>2)>
    <#assign prefix=prefix+"_"+pkg/>
  </#if>
</#list>
<#assign keys=""/>
<#assign auto=false/>
DROP TABLE IF EXISTS t${prefix}_${module_name};
CREATE TABLE t${prefix}_${module_name}
(
  c_id CHAR(${idLength}) NOT NULL COMMENT '主键',
<#if columns?? && (columns?size>0)>
<#list columns as column>
  ${column.name} ${column.type?upper_case} <#if column.number && !column.auto>DEFAULT 0<#else><#if column.notNull>NOT<#else>DEFAULT</#if> NULL</#if> COMMENT '${column.comment}',
  <#if column.unique><#assign keys=keys+",\n  UNIQUE KEY uk_"+column.name?remove_beginning("c_")+"("+column.name+") USING HASH"/></#if>
  <#if column.key><#assign keys=keys+",\n  KEY k_"+column.name?remove_beginning("c_")+"("+column.name+") USING HASH"/></#if>
  <#if column.auto><#assign auto=true/></#if>
</#list>
</#if>

  PRIMARY KEY pk(c_id) USING HASH${keys}
) ENGINE=InnoDB<#if auto> AUTO_INCREMENT=1</#if> DEFAULT CHARSET=utf8;
