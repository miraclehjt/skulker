<#assign prefix=""/>
<#list packages as pkg>
  <#if (pkg_index>2)>
    <#assign prefix=prefix+"_"+pkg/>
  </#if>
</#list>
<#assign keys=""/>
DROP TABLE IF EXISTS t${prefix}_${module_name};
CREATE TABLE t${prefix}_${module_name}
(
  c_id CHAR(${idLength}) NOT NULL COMMENT '主键',
<#if columns?? && (columns?size>0)>
<#list columns as column>
  ${column.name} ${column.type?upper_case} <#if column.number>DEFAULT 0<#else><#if column.notNull>NOT<#else>DEFAULT</#if> NULL</#if> COMMENT '${column.comment}',
  <#if column.unique><#assign keys=keys+",\n  UNIQUE KEY uk"+prefix+"_"+module_name+"_"+column.name?remove_beginning("c_")+"("+column.name+")"/></#if>
  <#if column.key><#assign keys=keys+",\n  KEY k"+prefix+"_"+module_name+"_"+column.name?remove_beginning("c_")+"("+column.name+")"/></#if>
</#list>
</#if>

  PRIMARY KEY pk${prefix}_${module_name}(c_id)${keys}
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
