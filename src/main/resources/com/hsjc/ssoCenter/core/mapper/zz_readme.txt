MyBatis 使用总结:
1、使用<where>标签时
1)、第一个条件不加and,最后一个条件不加',';
<where>
  <if test="email != null">
    email = #{email},
  </if>

  <if test="phone != null">
    and phone = #{phone},
  </if>

  <if test="userName != null">
    and userName = #{userName} #就是这里不要加',',否则会报错
  </if>
</where>

2、Java 获取mybatis插入数据后的主键值
1)、增加以下两个属性: useGeneratedKeys="true" keyProperty="id"(这个属性表示的是主键)
<insert id="insert" useGeneratedKeys="true" keyProperty="id" parameterType="java.util.HashMap">
    insert into tbrestfullog (clientId, actionId,
       description,actionTime
      )
    values (#{clientId}, #{actionId},
      #{description},#{actionTime}
      )
</insert>

2)、java代码
RestfulLog restfulLog = new RestfulLog();
restfulLog.setClientId(map.get("clientId").toString());
restfulLog.setActionId(Integer.parseInt(map.get("actionId").toString()));
restfulLog.setActionTime(new Date());
restfulLog.setDescription(map.get("description").toString());

restfulLogMapper.insert(restfulLog);

//获取主键Id
resultJson.put("requestSynId",restfulLog.getRestLogId());