# 주문 관리 API 개발 과제

-------------

- 사용기술 : Java11, Spring Boot2.7.5, JPA, H2, Gradle, Spring RestDocs
- Swagger 주소 : http://localhost:8080/swagger-ui/index.html
- h2 console 주소 : http://localhost:8080/h2-console/login.do

  
-------------  

> 1. API 응답구현
>> - 주문 접수처리 API 구현완료
>>> -  /api/orders/{id}/accept
>> - 주문 완료처리 API 구현완료
>>> - /api/orders/{id}/complete
>> - 단일 주문조회 API 구현완료
>>> - /api/orders/{id}
>> - 주문 목록조회 API 구현완료
>>> - /api/orders
  
-------------  

> 2. ERD
>> ![ERD](https://user-images.githubusercontent.com/103932247/202421385-c496916b-db71-4bf6-a066-7d604c4674b0.PNG)


  
-------------  

> 3. 시나리오
>> 1) 상품 등록 API를 호출하여 상품 정보를 등록한다.
>> 2) 주문 등록 API를 호출하여 상품에 대한 재고를 확인 후 주문 정보와 주문한 상품을 등록한다. 
>> 3) 주문한 상품의 개수만큼 상품의 재고에서 차감한다.
>> 4) 주문접수 API를 호출하여 주문 상태를 [ACCEPT]로 변경한다.
>> 5) 주문완료 API를 호출하여 주문 상태를 [COMPLETE]로 변경한다.
>> 6) 단일 주문조회 API를 호출하여 id에 대한 주문 정보를 조회한다.
>> 7) 주문 목록조회 API를 호출하여 모든 주문에 대한 리스트를 조회한다. 



------------- 

> 4. 검증결과
>> [RESTDocs 로 구현된 API 명세](https://haxxah1112.github.io/OrderManagement/index.html)

-------------  
   

