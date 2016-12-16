# Android 数据存储 - SQLite
虽然做 Android 时间也不短了，但是对于数据存储，以前都是用 `SharePreference` 解决的，能不用数据库就不用数据库，但最近的项目中要求必须使用数据库，那就学习下呗，这也是没办法的事情~

一般而言，数据库是用来保存重复或者结构化的数据的上上选。

没有使用过数据库的童鞋，也不用怕，菜鸟教程来一发 [SQLite 教程](http://www.runoob.com/sqlite/sqlite-tutorial.html)，多则半天，少则一两小时，数据库的基本概念以及操作包教包会，不会也别来找我。

```
// Todo
1. SQLite 简介
2. SQLiteOpenHelper
    - Cursor
    - getWriteableDatabase()
    - getReadableDatabase()
3. 导入已有数据
    - 导入 db 文件
    - 使用 SQL 语句创建数据库
4. 补充
    - database 的关闭
    - 查看 SQLite 版本号
    - SQLite ALERT 命令的局限
    - SQLite 设置主键自增
5. 遇到的问题
```

## SQLite 简介
SQLite 是一个进程内的库，实现了自给自足的、无服务器的、零配置的、事务性的 SQL 数据库引擎。它是一个零配置的数据库，这意味着与其他数据库一样，您不需要在系统中配置。
就像其他数据库，SQLite 引擎不是一个独立的进程，可以按应用程序需求进行静态或动态连接。SQLite 直接访问其存储文件。

### 为什么使用 SQLite
- 不需要一个单独的服务器进程或操作的系统（无服务器的）。
- SQLite 不需要配置，这意味着不需要安装或管理。
- 一个完整的 SQLite 数据库是存储在一个单一的跨平台的磁盘文件。
- SQLite 是非常小的，是轻量级的，完全配置时小于 400KiB，省略可选功能配置时小于250KiB。
- SQLite 是自给自足的，这意味着不需要任何外部的依赖。
- SQLite 事务是完全兼容 ACID 的，允许从多个进程或线程安全访问。
- SQLite 支持 SQL92（SQL2）标准的大多数查询语言的功能。
- SQLite 使用 ANSI-C 编写的，并提供了简单和易于使用的 API。
- SQLite 可在 UNIX（Linux, Mac OS-X, Android, iOS）和 Windows（Win32, WinCE, WinRT）中运行。

以上内容摘自 [SQLite 教程](http://www.runoob.com/sqlite/sqlite-tutorial.html)，关于 SQLite 更多的简介请移步 [Android 数据库开发（一）SQLite3概述](http://blog.csdn.net/itachi85/article/details/51649468)

## SQLiteOpenHelper
[Saving Data in SQL Databases](https://developer.android.com/training/basics/data-storage/databases.html)

## 导入已有数据的两种办法

### 拷贝 db 文件
某些情况下，我们希望程序初始的时候可以使用自带的数据库内容，而系统默认的数据库是放在 `/data/data/package/` 目录下的，那怎么样才能将自带的数据库导入呢？很简单，将自带数据库文件拷贝到该目录下即可。

```java
/**
* 打开外部数据库，正确的方法是将 raw 中的数据库文件拷贝到 /data/data/com.package.name/databases/your.db
*/
private void openOuterDatabase(String db) {
   try {
       File dbFile = new File(db);
       if (!dbFile.exists()) {
           InputStream is = mContext.getResources().openRawResource(R.raw.test);
           FileOutputStream fos = new FileOutputStream(dbFile);
           byte[] buffer = new byte[BUFFER_SIZE];
           int count = 0;
           while ((count = is.read(buffer)) > 0) {
               fos.write(buffer, 0, count);
           }
           fos.close();
           is.close();
       }
       mDatabase = SQLiteDatabase.openOrCreateDatabase(db, null);
   } catch (FileNotFoundException e) {
       Log.d(TAG, "File not found.");
       e.printStackTrace();
   } catch (IOException e) {
       Log.d(TAG, "IOException");
       e.printStackTrace();
   }
}
```

### 通过执行 SQL 语句写入已有数据

## 注意事项

### SQLite 关闭

### SQLite ALERT 语句的局限性
SQLite 不支持对列的改名和修改类型等操作，想要操作官方给出的方法是先备份原表数据到临时表，然后删除原表，再创建新的表结构，然后导入临时表的数据

### SQLite 设置主键自增

```sql
CREATE TABLE "Person" (
	 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	 "name" CHAR(50,0) NOT NULL,
	 "age" INT NOT NULL DEFAULT 0,
	 "address" CHAR(256,0)
);
```

### BUG

1. 当出现以下 Log 时，你就该考虑你数据库是否关闭了。

```log
Finalizing a Cursor that has not been deactivated or closed.
```
解决办法办法就是：**使用完 Cursor 记得调用 cursor.close()**

## 参考
1. [android之存储篇_SQLite数据库_让你彻底学会SQLite的使用](http://blog.csdn.net/jason0539/article/details/10248457)
2. [Android 导入外部数据库](http://zxs19861202.iteye.com/blog/1956796)
3. [Using your own sqlite database in android applications](http://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/)
4. [How to use an existing database with an android application](http://stackoverflow.com/questions/9109438/how-to-use-an-existing-database-with-an-android-application)
5. [SQL Features That SQLite Does Not Implement](http://www.sqlite.org/omitted.html)
6. [Saving Data in SQL Databases](https://developer.android.com/training/basics/data-storage/databases.html)