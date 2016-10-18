# mySpider
1.Simple codes to get all the majors the Graduate Record Examination.
2.Using the jsoup1.9.jar,please down it in the https://jsoup.org/
3.the httpPostThread.java fetching information in threads and httpPost.java is just using singel thread.

-----------------
还是中文吧：
1.这个是由于自己在研究生网上看对应的专业翻的头疼，就直接写了个获取专业信息的小程序，代码不长，很简陋，只需要用到jsoup-1.9.2.jar来解析对应的html信息。多线程的那个直接打印结果到控制台，单线程的可以写到文件里面，但是数据还需要再行处理。
由于是直接根据网站来扒的，定制化的还挺多的，参考看看就好了.
还需要优化的地方：
1.多线程：httpPostThread.java 增加写结果到文件。
2.多线程运行时前两个打出的都是null，需要查明原因。
----------------
  
