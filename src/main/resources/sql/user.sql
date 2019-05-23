# 查询用户列表
#sql ("userList")
select * from user order by id desc;
#end

# 根据登录的用户查询密码
#sql("select_password_byTelePhone")
select userPassword from user where telephone=?;
#end

# 增加用户
#sql("insertUser")
insert into user (username,realname,sex,userPassword,email,telephone,ID,regTime,address) values(?,?,?,?,?,?,?,?,?);
#end

# 删除用户
#sql("deleteUser")
delete from user where userID=?;
#end

# 修改用户
#sql("updateUser")
# update user set(username,realname,sex,userPassword,email,telephone,ID,regTime,address)=(?,?,?,?,?,?,?,?,?) where userID=?;
update user set username=?,realname=?,userPassword=?,email=?,telephone=?,ID=?,address=? where userID=?;
#end

# 查询登录输入的手机号是否存在
#sql("select_login_phone")
select * from user where telephone=?;
#end
#根据手机号查用户名字放在session中
#sql("select_username_byphone")
select username from user where telephone=?;
#end
#根据手机号更改密码
#sql("update_password_byphone")
update user set userPassword=? where telephone=?;
#end
#根据手机号检查原密码
#sql("select_original_password")
select userPassword from user where telephone=?;
#end
# 注册时查询手机号是否已经注册
#sql("checkphoneexists")
select * from user where telephone=?;
#end

# 根据id查询商品信息
#sql("selectgoods_byid")
select * from goods where id=?;
#end
# 根据用户手机号查询到用户id
#sql("select_user_id_byphone")
select userID from user where telephone=?;
#end
# 更新商品表中的商品的数量
#sql("update_goods_nummber_byid")
update goods set number=? where id=?;
#end
# 查询登录的用户添加到购物车中的所有商品的id
#sql("sql_select_cart_goodsId_byloginphone")
select count(id) as number, goods_id, u_id from cart  group by goods_id having u_id=?;
#end
# 根据商品id查询商品名称
#sql("select_goodsName_bygoodsId")
select goodsName from goods where id=?;
#end
# 购物车表和商品表建立内连接通过用户id查询所需要的记录
#sql("sql_select_inner_join_byuid")
select  count(x.id) as number, x.goods_id,y.goodsName, y.goodsPrice, y.goodsImage,y.Manufacturename,y.goodsDescription
from cart x inner join goods y on x.goods_id=y.id where x.u_id=? group by x.goods_id;
#end

#sql("select_number_id_bygoodsidanduid")
# select count(id) as number, goods_id, u_id from cart   group by goods_id having (u_id,goods_id)=(?,?);
select count(id) as number, goods_id, u_id from cart   group by u_id,goods_id having (u_id,goods_id)=(?,?);

#end
#删除购物车表中的数据
#sql("delete_cart_bygoodsidanduid")
delete from cart where goods_id=? and u_id=?;
#end
# 更新商品表中的商品数据
#sql("update_goods_number")
update goods set number= number-1 where id= ?;
#end
#sql("update_cart_end_record")
delete from cart where id in (select id from (select id from cart order by id desc limit 1) as T);
#end
#sql("update_goods_end_number")
update goods set number=0 where id=?;
#end

#测试
#sql("select_cartid_byu_idandgoods_id")
select id from cart where u_id=? and goods_id=?;
#end
#sql("selectcartgoods_nummber")
select number from cart where goods_id=? and u_id=?
#end
