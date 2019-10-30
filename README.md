##  edith demo
# 资料
[Spring](https://spring.io/guides) 

[Spring Web](https://spring.io/guides/gs/serving-web-content/)

[GitHub OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

[Okhttp](https://square.github.io/okhttp/)

[flyWay](https://flywaydb.org/getstarted/)

## 工具

[Git](https://git-scm.com/download)   
[BootStrap](https://v3.bootcss.com/components)


## 脚本
```
create table USER
(
	ID INTEGER default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_48150C97_2675_4589_B0FC_99D31EFBF812) auto_increment,
	NAME VARCHAR(100),
	ACCOUNT_ID VARCHAR(100),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	NEW_COLUMN INTEGER
);


```