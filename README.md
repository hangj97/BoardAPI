# BoardAPI 명세서

| 기능 | Method | URL | Request | Request header | Response | Response header |
| --- | --- | --- | --- | --- | --- | --- |
| 회원가입 | POST | /auth/signup | {
”username”:”username”,
”password”:”password”
} |  | {
”msg”:”회원가입 성공”,
”statusCode”: 200
} |  |
| 로그인 | POST | /auth/login | {
”username”:”username”,
”password”:”password”
} |  | {
”msg”:”로그인 성공”,
”statusCode”:200
} | <JWT> |
| 게시글 전체 조회 | GET | /api/boards | - |  | {
{
"createdAt": "2022-07-25T12:43:01.226062”,
"modifiedAt": "2022-07-25T12:43:01.226062”,
"id": 1,
"title": "title2",
"contents": "content2",
"username": "author2"
},
{
"createdAt": "2022-07-25T12:43:01.226062”,
"modifiedAt": "2022-07-25T12:43:01.226062”,
"id": 2,
"title": "title",
"contents": "content",
"username": "author"
}
…
} |  |
| 게시글 상세 조회 | GET | /api/board/{id} | - |  | {
"createdAt": "2022-07-25T12:43:01.226062”,
"modifiedAt": "2022-07-25T12:43:01.226062”,
"id": 1,
"title": "title2",
"contents": "content2",
"username": "author2"
} |  |
| 게시글 등록 | POST | /api/board | {
"title" : "title",
"contents" : "content",
"username" : "author",
"password" : "password"
} | <JWT> | {
"createdAt": "2022-07-25T12:43:01.226062”,
"modifiedAt": "2022-07-25T12:43:01.226062”,
"id": 1,
"title": "title",
"contents": "content",
"username": "author"
} |  |
| 게시글 수정 | PUT | /api/board/{id} | {
"title" : "title2",
"contents" : "content2",
"username" : "author2",
"password" :"password2"
} | <JWT> | {
"createdAt": "2022-07-25T12:43:01.226062”,
"modifiedAt": "2022-07-25T12:43:01.226062”,
"id": 1,
"title": "title2",
"contents": "content2",
"username": "author2"
} |  |
| 게시글 삭제 | DELETE | /api/board/{id} |  | <JWT> | {
”msg”:”게시글 삭제 성공”,
”statusCode”:200”
}
{
”msg”: “삭제 권한이 없습니다.”,
”statusCode”: 400
} |  |
| 댓글 등록 | POST | /api/comment/{id} | {
”content”:”content”
} | <JWT> | {
"createdAt": "2022-07-25T12:43:01.226062”,
"modifiedAt": "2022-07-25T12:43:01.226062”,
"id": 1,
"contents": "content2",
"username": "author2"
} |  |
| 댓글 수정 | PUT | /api/comment/{id} | {
”content”:”content”
} | <JWT> | {
"createdAt": "2022-07-25T12:43:01.226062”,
"modifiedAt": "2022-07-25T12:43:01.226062”,
"id": 1,
"contents": "content2",
"username": "author2"
}

{
”msg”:”댓글 수정 권한이 없습니다.””,
”statusCode”: 200 |  |
| 댓글 삭제 | DELETE | /api/comment/{id} |  | <JWT> | {
”msg”:”댓글 삭제 성공”,
”statusCode”:200
}

{
”msg”: “삭제 권한이 없습니다.”,
”statusCode”: 400
} |  |
