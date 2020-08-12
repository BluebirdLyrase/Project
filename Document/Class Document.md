# stackoverflow.APIConnecter

​	pack age นี้มีไว้สำหรับ class ที่ใช้ติดต่อกับ API ของ StackExchange



## StackOverFlowConnecter

​	เป็น class แม่ของทุก class ใน stackoverflow.APIConnecter มีไว้สำหรับรับข้อมูลจาก url ของ api แล้วแปลงข้อมูลที่ได้ให้กลายเป็น JSONObject

### JSONObject readJsonFromUrl(String url) 

​	 รับ url มาเป็น parameter และดึงข้อมูลมาจาก API และแปลงเป็น JSONObject

### String readAll(Reader rd)

​	 ถูกเรียกใช้จากจาก **readJsonFromUrl(String url)** เท่านั้น แปลงข้อมูล Reader ให้กลายเป็น String 

## AllContent 

​	extends StackOverFlowConnecter

​	มีหน้าที่เรียกใช้ api เพื่อรับข้อมูล จาก url ที่สร้างขึ้นจาก question_id ที่ได้รับมา

​	** การใช้งานตัวแปรต่างๆใน AllConetent Object สามารถดูข้อมูลได้ในหัวข้อ package stackoverflow.DataClass ของเอกสารฉบับนี้

### AllContent(String question_id,boolean isOffline)

​	เรียกใช้เพื่อดึงข้อมูลกระทู้จาก StackExchangeAPI โดยจะใช้โครงสร้างจาก Data Class ใน stackoverflow.DataClass ได้แก่ Answer Question และ Comment โดยจะแบ่งการสร้าง JSONObject เป็นสองประเภท ขึ้นอยู่กับ parameter isOffline

**API ที่ใช้ :**  https://api.stackexchange.com/docs/questions-by-ids#order=asc&sort=activity&ids=11227809&filter=!6CZol-kjk43Caeu4wbmgfWPFBKTl-6MgX9_mx25H6._QEcG9r2lN3QrdeDe&site=stackoverflow&run=true

**parameter**

​	**question_id :** ID ของกระทู้ใน API ของ StackExchange ตัวอย่างเช่น

​	https://stackoverflow.com/questions/63216077/how-to-store-sublist-operation-on-an-immutable-arraylist-in-another-immutable-ar

​	โดยที่ 63216077 คือตัว question_id

​	**boolean isOffline :** มีไว้เพื่อตรวจสอบว่าถูกเรียกใช้งานใน โหมด Offline หรือเปล่า 

​		**true : ** ใช้งานโหมดออฟไลน์ จะสร้าง JSONObject โดยดึงข้อมูลจากไฟล์ในเครื่องของผู้ใช้

​		**false : ** ใช้งานโหมดออนไลน์ จะสร้าง JSONObject โดยดึงข้อมูลจาก API ของ StackExchange



### Question getAllConetent()

​	ถูกเรียกใช้ใน ContentView เพื่อเรียกใช้ข้อมูลต่างๆในกระทู้



### JSONObject getJsonObject()

​	เคยถูกใช้เพื่อฟีเจอร์การ Save Offline แต่ปัจจุบันเปลี่ยนไปใช้ method ใน class AllContentObjectOnly แทน



## AllContentObjectOnly

​	extends StackOverFlowConnecter

​	ถูกเรียกใช้ในฟีเจอร์ Save Offline เท่านั้น

### JSONObject getJsonObject(String question_id)

​	รับ  question_id มาเพื่อสร้าง url ฬนการติดต่อ และ รับข้อมูลจาก StackExchange API



## SearchResult

​	มีหน้าที่ติดต่อกับ API เพื่อรับข้อมูลรายการผลการค้นหา 

### SearchResult(String intitle, int page, int pageSize, String order, String sort, String site, String tagged)

รับ parameter มาเพื่อสร้าง url และใช้ url ในการติดต่อ API เพื่อรับข้อมูลรายการผลการค้นหา 

**API ที่ใช้ :** https://api.stackexchange.com/docs/advanced-search#page=1&pagesize=40&order=asc&sort=relevance&q=What&accepted=True&tagged=java%3Bc%2B%2B&filter=!4(L6lo0bkjbSB1w_D&site=stackoverflow&run=true

โดยจะรับค่า titleList ซึ่งคือหัวข้อของกระทู้ และ questionIdList ซึ่งคือรายการ ID ของกระทู้ตามลำดับเดียวกับ titleList 

**parameter**

**intitle : **ข้อความ text ที่ใช้ในการค้นหา

**page : ** หมายเลขหน้าของรายการ (1)

**pageSize : ** ขนาดของหน้ารายการ กำหนดว่าจะได้รับหัวข้อสูงสุดกี่กระทู้

**order :** กำหนดว่าจะเรียกลำดับรายการกระทู้แบบ asc หรือ

**sort :** กำหนดว่าจะเรียกลำดับรายการกระทู้โดยอ้างจากตัวแปรใด

**site :** กำหนดว่าจะค้นหารายการกระทู้จากเว็บไซต์ใเในเครือของ StackExchange

**tagged :** กำหนดว่ารายการที่ค้นหาจะต้องติด tag ใดบ้างโดยแยกแต่ละ tag ด้วยเครื่องหมาย ; 



### Boolean haveResult()

​	ตรวจสอบว่าการค้นหาของผู้ใช้มีผลลัพธุ์ออกมาหรือไม่ โดยตรวจสอบจากตัว JSONArray หากเป็น [] จะ return ค่า false 



# stackoverflow.DataClass

## Question

​	ประกอบไปด้วย

	private String id; // question_id ของกระทู้ถาม
	private String title; // หัวข้อกระทู้
	private String body; // เนื้อหาของคำถามในกระทู้ถาม
	private Comment[] comment; // object ของ Comment ซึ่งก็คือข้อมูล คอมเม้นของกระถาม
	private Answer[] answer; // object ของคำตอบ
	private boolean haveComment; 
	private boolean haveAnswer;
	private String owner; // ชื่อผู้ตั้งกระทู้ถาม
	private String ownerImage; // รูปภาพโปรไฟล์ (url) ของผู้ตั้งกระทู้ถาม
	private int score; // คะแนนของกระทู้ถาม
	private String[] tags;
	private boolean haveTags;
## Answer

​	ประกอบไปด้วย Comment

## Comment

