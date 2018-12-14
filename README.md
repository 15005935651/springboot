# springboot
一个简单的SpringBoot后台应用,小型电商系统，用来学习...没有前端，直接url测试后端。

1获取OTP短信验证码：
http://localhost:8080/user/getOTP?telephone=15005935651

2通过验证码注册：
http://localhost:8080/user/register?telephone=15005935651&name=zxx&age=15&password=123&otpCode=63629&gender=1


3通过手机号和密码登录：
http://localhost:8080/user/login/?telephone=15005935651&password=123


4添加商品（可以未登录）
http://localhost:8080/item/createItem?title=hotdog&description=good&price=100&stock=77&imgUrl=baidu

5查询通过商品id查询商品（可以未登录）：
http://localhost:8080/item/getItem?id=1

6查询所有商品列表（可以未登录）：
http://localhost:8080/item/list

7根据商品id下订单（需要登录）
http://localhost:8080/order/orderCreate?itemId=1&amount=1

