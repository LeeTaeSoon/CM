## 이태순
#### 2. CM 설정 파일들
적절한 디렉토리에 설정 파일들을 놓고 파일들의 기본 값을 설정해야 한다.

##### 2.1 CM 서버 설정 파일
서버 어플리케이션은 반드시 CM 서버 설정 파일을 세팅해야한다. 메인 파일은 *cm-server.conf* 파일이다. 이 파일은 단순한 텍스트 파일로 어떤 텍스트 편집기로도 열 수 있다. 아래 그림은 파일의 예시이다.

###### 2.1.1 기본적인 통신 서비스의 설정
많은 설정 필드들은 이미 기본값으로 설정되어있기 때문에 개발자가 해야할 일은 기본 서버의 주소 정보를 설정하는 것이다. CM 은 다수의 서버를 설정할 수 있고 기본 서버와 추가적인 서버들로 구성된다. 기본 서버는 모든 클라이언트와 항상 연결되어야하는 서버이다. 그러므로 CM 을 사용하는 서버 어플리케이션은 기본 서버나 추가 서버가 될 수 있다. 단일 서버 시스템의 경우 서버 어플리케이션은 기본 서버이다. 이 예시에서 우리는 단일 서버를 사용한다. 아래의 설정 필드들은 기본 서버와 이 서버 어플리케이션의 주소와 포트를 설정하는데 사용된다.
- ```SERVER_ADDR``` : 기본 서버의 IP 주소
- ```SERVER_PORT``` : 기본 서버의 포트 번호
- ```MY_PORT``` : 서버 어플리케이션의 포트 번호. 만약 기본 서버라면 이 값은 반드시 ```SERVER_PORT``` 값과 같아야 한다.

###### 2.1.2 확장된 통신 서비스들의 설정
서버 설정 파일에서 개발자는 CM 에 의해 제공되는 다른 통신 관련 정책들 또한 설정할 수 있다. 세부사항은 아래에서 설명한다.
- ```SYS_TYPE``` : 어플리케이션 타입. 클라이언트-서버 모델에서 서버 어플리케이션은 ```SERVER```로서, 클라이언트는 ```CLIENT```로서 이 필드를 반드시 설정해야한다.
- ```COMM_ARCH``` : 통신 구조. 이 필드는 CM 을 사용하는 어플리케이션의 통신 구조를 지정한다. 가능한 값들로는 클라이언트-서버 모델로 ```CM_CS```, 클라이언트-서버 모델에 추가적으로 멀티캐스트 통신을 사용하는 하이브리드 모델로 ```CM_PS```가 있다.
- ```UDP_PORT``` : 서버 어플리케이션의 기본 포트 번호. CM 을 사용하는 어플리케이션들은 UDP 연결을 통해 메시지를 전송할 수 있다. 이 필드는 서버 CM 이 시작될 때 열리는 기본 UDP 연결을 위한 서버 어플리케이션의 포트 번호를 설정한다.
- ```MULTICAST_PORT``` : 기본 멀티캐스트 포트 번호. 만약 CM 서버가 하이브리드 통신 구조로 설정한다면 서버 어플리케이션은 이 포트 번호를 기본 멀티캐스트 그룹을 통한 멀티캐스트 이벤트를 받는데 연결한다.
- ```SIM_TRANS_DELAY``` : CM 에 의한 이벤트 전송 당 밀리 초 단위의 인공적인 딜레이를 추가한다. 만약 이 값이 0 보다 크다면 CM 의 이벤트를 보내는 쓰레드는 CM 이벤트를 보내기 전에 이 값 동안 대기한다. 이 필드는 다른 네트워크 조건 효과의 시뮬레이션에만 사용된다. 이 값은 시뮬레이션 딜레이 없이 이벤트를 보내기 위해 0 이어야 한다.

###### 2.1.3 파일 전송 서비스 설정
CM 은 아래 설정 필드들의 값들에 따라 다른 파일 전송 기능들을 지원한다.
- ```FILE_PATH``` : 파일 전송을 위한 기본 경로. CM 은 파일 전송을 위해 이 경로를 참조한다. 만약 서버나 클라이언트 어플리케이션이 파일을 요청받으면 어플리케이션은 이 경로에서 요청받은 파일을 검색한다. 만약 클라이언트 어플리케이션이 파일을 받으면 받은 파일은 이 디렉토리 안에 저장된다. 만약 서버 어플리케이션이 파일을 받으면 받은 파일은 이 경로의 다른 서브 디렉토리에 저장된다. 서브 디렉토리는 파일을 보낸 클라이언트의 이름이다. 만약 기본 경로가 존재하지 않으면 CM 은 필요할때 이를 만들거나 이것의 서브 디렉토리를 만든다.
- ```FILE_TRANSFER_SCHEME``` : 파일 전송 정책 (0 또는 1). CM 은 두 가지 종류의 파일 전송 서비스를 제공한다. 만약 이 필드의 값이 0 이면, CM 은 파일을 보내고 받는데 기본 TCP 소켓 채널을 사용한다. 만약 이 필드의 값이 1 이면, CM 은 별도의 blocking TCP 소켓 채널들과 쓰레드을 사용해 파일을 전송하는 고급 파일 전송 서비스를 사용한다.
- ```FILE_APPEND_SCHEME``` : 파일 리셉션 정책 (0 또는 1). 만약 이 값이 0 (덮어쓰기 모드) 이면 파일 리시버는 항상 리시버가 이미 파일의 일부를 가지고 있더라도 전체 파일을 받기 시작한다. 만약 이 값이 1 (이어 받기 모드) 이면 리시버는 만약 리시버가 이미 파일의 일부를 가지고 있다면 파일의 남은 바이트만 받기 시작한다.

###### 2.1.4 CM 유저 인증 및 세션 정책들 설정
- ```LOGIN_SCHEME``` : 유저 인증 정책. 이 필드는 서버의 유저 인증 처리 여부를 나타낸다. CM 에서 클라이언트 (또는 유저) 는 반드시 다른 클라이언트들과 상호작용하기 전에 기본 서버에 로그인 해야한다. 만약 이 값이 0 (false 값) 이면 서버는 로그인 요청을 받을 때 유저인지 인증하는 작업을 수행하지 않고 서버 CM 은 항상 요청을 받아들인다. 만약 이 값이 1 (true 값) 이면 서버는 유저 인증 작업을 수행한다. 서버 어플리케이션은 자체 인증 방법을 사용하거나 CM 의 인증을 사용할 수 있다. 만약 CM 이 데이터베이스를 사용하도록 설정한다면 (서버 설정 파일에서 설정할 수 있다.) 서버 CM 은 DB 에 등록된 유저 정보로 유저인지 인증한다. 그렇지 않으면 서버 어플리케이션은 반드시 별도의 인증 방법을 가지고 인증 결과를 요청한 클라이언트에게 알려야한다.
- ```SESSION_SCHEME``` : 멀티 세션 정책. 이 필드로 개발자는 어플리케이션이 하나 또는 여러 세션을 사용할 것임을 결정할 수 있다. CM 에서 유저들은 세션과 그룹의 개념으로 계층적으로 그룹지어질 수 있다. 서버 어플리케이션은 다수의 세션을 설정할 수 있고 세션은 다수의 그룹을 포함할 수 있다. 유저는 항상 세션과 그룹에 속해야하고 그/그녀는 세션이나 그룹안의 다른 유저와 상호작용할 수 있다. 만약 이 필드의 값이 0 이면 CM 은 다중 세션을 사용하지 않고 하나의 기본 세션만을 사용한다. 이 경우 유저가 서버에 로그인 할 때 그/그녀는 자동으로 이 세션과 기본 그룹에 참여한다. 만약 이 값이 1 이면 서버 어플리케이션은 유저가 그들 중 하나에 참여하도록 선택할 수 있게 하기 위해 다중 세션을 설정할 수 있다. 세션 관리의 세부사항은 다른 섹션에서 나중에 다룰 것이다.

###### 2.1.5 SNS 컨텐츠 다운로드 정책 설정
- ```DOWNLOAD_SCHEME``` : SNS 컨텐츠 전송 정책 (0 또는 1). 이 필드는 서버가 얼마나 많은 SNS 컨텐츠를 클라이언트에게 전달할 수 있는지 나타낸다. 만약 이 값이 0 이면 CM 은 ```DOWNLOAD_NUM``` 필드에 명시된 SNS 컨텐츠의 고정된 양을 쓴다. 만약 이 값이 1 이면 CM 은 동적 다운로드 방식을 사용한다.
- ```DOWNLOAD_NUM``` :  사용자가 SNS 컨텐츠 다운로드를 요청할 때 다운로드 되는 SNS 컨텐츠 아이템의 기본 개수
- ```ATTACH_DOWNLOAD_SCHEME``` : SNS 컨텐츠의 이미지 첨부 파일 다운로드 방식 (0 : 풀 모드, 1 : 부분 모드, 2 : 선인출 모드, 3 : 모드 없음). 풀 모드에서 CM 서버는 클라이언트에게 원본 퀄리티의 이미지를 보낸다. 부분 모드에서 CM 서버는 원본 이미지 대신에 썸네일 이미지를 보낸다. 모드가 없는 경우 서버는 이미지의 텍스트 링크만 보낸다. 선인출 모드에서 서버는 클라이언트에게 썸네일 이미지를 보내고 클라이언트가 원하면 원본 이미지 또한 보낸다.
- ```ATTACH_ACCESS_INTERVAL``` : 선인출 모드를 사용한 유저 접근 기록의 조사 일수. 이 필드는 ```ATTACH_DOWNLOAD_SCHEME``` 필드가 선인출 모드일 경우만 사용된다.
- ``` ATTACH_PREFETCH_THRESHOLD``` : 선인출 한계값 (0 ~ 1). 선인출은 추정된 관심 비율이 이 한계값을 넘을 때 일어난다. 이 필드는 ```ATTACH_DOWNLOAD_SCHEME``` 가 선인출 모드일 경우만 사용된다.
- ```THUMBNAIL_HOR_SIZE``` : SNS 컨텐츠에 첨부된 생성된 썸네일 이미지의 가로 길이. CM 클라이언트가 이미지를 언제 업로드하건 CM 서버는 알맞은 썸네일 이미지를 만들고 저장한다.
- ```THUMBNAIL_VER_SIZE``` : SNS 컨텐츠에 첨부된 생성된 썸네일 이미지의 높이. 만약 이 값이 0 이면 수직 스케일은 원본 이미지 크기와 비교해 수평 스케일 인자에 비례한다.

###### 2.1.6 CM DB 사용법 설정
- ```DB_USE``` : DB 사용 플래그. 이 필드는 서버 어플리케이션이 CM 의 내부 DB 를 사용할 것인지 아닌지 설정한다. 서버 CM 은 내부적으로 MySQL DB 를 자체 사용자 관리 및 SNS 컨텐츠 관리를 위해 사용한다. 만약 이 값이 0 이면 어플리케이션은 CM DB 를 사용하지 않는다. 만약 이 값이 1 이면 어플리케이션은 CM DB 를 사용하고 추가적인 DB 정보를 반드시 설정해야 한다. MySQL 을 사용하는 CM DB 설정 방법의 세부사항은 이 문서의 "CM DB 관리를 위한 MySQL 서버 설정" 섹션에서 설명한다.
  - ```DB_HOST``` : DB 의 호스트네임 또는 IP 주소. 만약 DB 가 서버 어플리케이션과 같은 머신에 설치되어 있다면 이 값은 'localhost' 이다.
  - ```DB_USER``` : DB 의 사용자 이름
  - ```DB_PASS``` : DB 의 비밀번호
  - ```DB_PORT``` : DB 의 포트 번호
  - ```DB_NAME``` : DB 이름

###### 2.1.7 다중 세션 설정
- ```SESSION_NUM``` : 세션의 개수. 이 값은 반드시 CM 이 최소 1 개 또는 그 이상의 세션을 사용하기 때문에 1 보다 크거나 같아야 한다. 서버 CM 이 시작할 때 여기 설정된 대로 세션이 생성된다. 제공된 server-cm.conf 파일에는 세 개의 세션들이 이미 설정되어있다. 세션의 개수에 따라 개발자는 또한 각 세션들에 아래 필드들을 설정해야 한다.
  - ```SESSION_FILE#``` : 세션 설정 파일의 이름. '#' 은 세션을 구별하기 위한 정수이다 (1 로 시작하는). 세션 설정 파일에서 개발자는 다음 섹션에서 설명할 세션의 그룹 정보를 설정한다.
  - ```SESSION_NAME#``` : 세션 이름. '#' 은 세션을 구별하기 위한 정수이다 (1 로 시작하는). CM 의 세션 이름이 식별자이므로 고유한 이름이 할당되어야 한다.

##### 2.2 CM 세션 설정 파일
세션의 개수, 설정 파일의 이름과  cm-server.conf 파일의 세션 이름에 따라서 개발자는 세션 설정 파일을 설정해야 한다. 각 파일에서 개발자는 그룹의 수, 그룹의 이름들, 그들의 멀티캐스트 주소들과 같은 각 세션의 그룹 정보를 설정한다. 아래 그림은 세션 설정 파일의 예시이다.

세션 설정 파일에서 요구되는 필드들은 아래와 같다.
- ```SESSION_NAME``` : 세션 이름. 이 값은 반드시 server-cm.conf 파일의 이름과 같아야 한다.
- ``` GROUP_NUM``` : 그룹의 수. 이 값은 CM 이 최소 하나나 그 이상의 그룹을 사용하기 때문에 1 보다 크거나 같아야 한다. 서버 CM 이 시작할 때 여기 설정된대로 그룹들을 만든다. 각 그룹들에 개발자는 그룹 이름, 그룹 주소, 그룹 포트 번호를 설정해야 한다.
  - ```GROUP_NAME#``` : '#' 은 그룹을 구별하기 위한 정수이다 (1로 시작하는). CM 에서 그룹 이름은 식별자이므로 고유한 이름이 할당되어야 한다.
  - ```GROUP_ADDR#``` : '#' 은 그룹 멀티캐스트 주소이다. '#' 은 그룹을 구별하기 위한 정수이다 (1로 시작하는). 만약 CM 의 통신 아키텍처가 ```CM_PS``` 모델로 설정되었다면 그룹의 유저는 멀티캐스트 채널에서 멀티캐스트 채널을 통해 같은 그룹의 다른 유저들과 통신할 수 있다. 그러므로 각 그룹은 이런 목적을 위해 멀티캐스트 주소를 할당받아야 한다.
  - ```GROUP_PORT#``` : '#' 은 그룹을 구별하기 위한 정수이다 (1로 시작하는).

##### 2.3 CM 클라이언트 설정 파일
클라이언트 어플리케이션은 반드시 CM 클라이언트 설정 파일을 설정해야 한다. 메인 파일은 *cm-client.conf* 파일이다. 아래 그림은 이 파일의 예시이다.

CM 서버 설정 파일과 달리 CM 클라이언트 설정 파일은 그리 많은 필드를 가지고 있지 않다. 개발자가 설정해야할 것은 시스템 타입, 기본 서버 정보, UDP 포트 번호, 아래 보이는 파일 경로 정보이다.
- ```SYS_TYPE``` : 어플리케이션 타입. 클라이언트-서버 모델에서 클라이언트 어플리케이션은 이 필드를 ```CLIENT```로 설정해야 한다.
- ```SERVER_ADDR``` : 기본 서버의 IP 주소
- ```SERVER_PORT``` : 기본 서버의 포트 번호
- ``` UDP_PORT``` : 서버 어플리케이션의 기본 포트 번호. CM 을 사용하면 어플리케이션들은 UDP 연결을 이용해 메시지를 전송할 수 있다. 이 필드는 서버 CM 이 시작될 때 열리는 기본 UDP 연결을 위한 서버 어플리케이션의 포트 번호를 설정한다.
- ```FILE_PATH``` : 파일 전송을 위한 기본 경로. CM 은 파일 전송을 위해 이 경로를 참조한다. 만약 클라이언트 어플리케이션에 파일이 요청되면 이 경로에서 파일을 검색한다. 만약 클라이언트 어플리케이션이 파일을 받으면 받은 파일은 이 경로에 저장된다. 만약 기본 경로가 없다면 필요할 때 CM 이 생성한다.

위의 기본적인 설정에 추가로 CM 클라이언트는 아래 설정 필드들 또한 설정할 수 있다.
- ```MULTICAST_ADDR``` : 기본 멀티캐스트 주소. 만약 CM 서버가 하이브리드 통신 아키텍처를 설정한다면 서버 어플리케이션은 이런 멀티캐스트 주소에 참여한다.
- ```MULTICAST_PORT``` : 기본 멀티캐스트 포트 번호. 만약 CM 서버가 하이브리드 통신 아키텍처를 설정한다면 서버 어플리케이션은 기본 멀티캐스트 그룹을 통해 멀티캐스트 이벤트를 받을 이 포트 번호를 연결한다.
- ```FILE_APPEND_SCHEME``` : 파일 리셉션 모드 (0 또는 1). 만약 이 값이 0 (덮어쓰기 모드) 이면 파일 리시버는 항상 리시버가 이미 파일의 일부를 가지고 있더라도 전체 파일을 받기 시작한다. 만약 이 값이 1 (이어 받기 모드) 이면 리시버는 만약 리시버가 이미 파일의 일부를 가지고 있다면 파일의 남은 바이트만 받기 시작한다.
- ```SIM_TRANS_DELAY``` : CM 에 의한 이벤트 전송 당 밀리 초 단위의 인공적인 딜레이를 추가한다. 만약 이 값이 0 보다 크다면 CM 의 이벤트를 보내는 쓰레드는 CM 이벤트를 보내기 전에 이 값 동안 대기한다. 이 필드는 다른 네트워크 조건 효과의 시뮬레이션에만 사용된다. 이 값은 시뮬레이션 딜레이 없이 이벤트를 보내기 위해 0 이어야 한다.

#### 3. CM 네트워크에 참여하기
##### 3.1 어플리케이션에서 CM 의 초기화
개발자가 CM 설정을 끝내면 그/그년는 이제 CM 을 사용한 클라이언트-서버 어플리케이션을 구현하기 위한 준비가 됬다.

개발자는 그/그녀의 어플리케이션을 위한 클래스들을 만들 수 있다. 이 가이드에서 우리는 서버와 클라이언트 어플리케이션의 이름을 각각 ```CMServerApp```, ```CMClientApp``` 으로 한다.

CM 을 초기화하고 시작하기 위해 서버와 클라이언트 어플리케이션 모두 CM 패키지 클래스를 ```import```하고 적절한 CM stub 클래스의 객체를 선언, CM 이벤트 핸들러 객체 설정, stub 클래스의 start 메소드를 호출해야 한다. 일단 CM 이 초기화되고 실행되기 시작하면 어플리케이션은 stub 클래스를 통해 제공되는 CM APIs 를 호출할 수 있다.

###### 3.1.1 서버 어플리케이션의 CM 초기화
아래 그림은 CM 을 초기화하는 서버 어플리케이션 클래스 (```MServerApp```) 을 보여준다.

이 ```CMServerApp``` 클래스에서 ```CMServerStub``` 변수는 서버 어플리케이션이 CM 서버 stub 모듈을 사용하기 위해 선언된다. 어플리케이션 객체가 ```main``` 메소드에서 만들어 진 후, 다음에 해야할 일은 CM 이벤트 핸들러를 설정하는 것이다. 개발자가 이벤트 핸들러를 설정한 후 어플리케이션은 원격 CM 객체로부터 받는 모든 CM 이벤트를 캐치할 수 있다. 이벤트 핸들러를 정의하는 세부사항은 다음 서브섹션에서 설명한다. stub 객체의 ```startCM``` 메소드를 호출함으로써 CM 은 CM 서버 설정 파일에 의해 설정된대로 초기화하고 실행을 시작한다.

###### 3.1.2 CM 이벤트 핸들러
이전 예제에서 이벤트 핸들러 객체 (```m_eventHandler```)가 생성되고 ```setEventHandler``` 메소드를 통해 CM stub 모듈이 설정된다. 이벤트 핸들러는 언제 받는지 상관없이 CM 이벤트를 받는 역할을 한다. 아래 그림에서 볼 수 있듯이 개발자는 특정 CM 이벤트가 받아졌을 때 서버 어플리케이션이 어떤 작업을 할 수 있도록 어플리케이션 코드를 포함하는 이벤트 핸들러 클래스를 정의할 수 있다.

이벤트 핸들러 클래스 ```CMServerEventHandler``` 는 반드시 이벤트 처리 메소드 ```processEvent``` 를 정의하는 ```CMEventHandler``` 인터페이스를 *implement* 한다. 위의 예제에서 클래스 생성자는 이벤트 핸들러가 때때로 받아진 CM 이벤트에 대한 응답으로 CM APIs 호출을 하기 위해 stub 모듈에 엑세스해야할 수 있기 때문에 CM 서버 stub 을 참조하는 하나의 파라미터를 가진다. ```processEvent``` 메소드는 ```CMEvent``` 클래스의 이벤트를 받아 개발자의 이벤트 처리 함수를 정의한다. 위의 예제는 이벤트 핸들러가 타입이 세션 이벤트와 그 ID 가 ```CMSessionEvent.LOGIN```인 CM 이벤트를 받았을 때, 이벤트의 메시지를 출력하는 것을 보여준다. ```LOGIN``` 이벤트는 클라이언트가 CM 서버에 로그인을 요청했을 때 CM 클라이언트로부터 전달된다. CM 이벤트의 자세한 사용법은 다른 섹션에서 예제 코드와 함께 설명한다. 일단 개발자가 이벤트 처리 함수를 구현하고 CM stub 에 이벤트 핸들러를 설정하면 ```processEvent``` 메소드는 원격 CM 노드로부터 언제 이벤트를 받아도 호출될 것이다.

###### 3.1.3 클라이언트 어플리케이션의 CM 초기화
아래 그림은 CM 을 초기화하는 클라이언트 어플리케이션 클래스 (```CMClientApp```) 을 보여준다.

이 ```CMClientApp``` 클래스의 ```CMClientStub``` 변수는 클라이언트 어플리케이션이 CM 클라이언트 stub 모듈을 사용하기 위해 선언된다. 어플리케이션 객체가 ```main``` 메소드에서 만들어진 후 다음에 해야할 일은 CM 이벤트 핸들러를 설정하는 것이다. 이벤트 핸들러를 정의하는 방법은 서버 이벤트 핸들러의 경우와 같다. stub 객체의 ```startCM``` 메소드를 호출함으로써 CM 은 CM 클라이언트 파일에 의해 설정된대로 초기화하고 실행을 시작한다. 이 단계는 클라이언트의 ```CM_INIT``` 상태로 불린다.

만약 서버와 클라이언트 어플리케이션들이 성공적으로 CM 을 초기화하면 ```CM_INIT``` 상태의 클라이언트 또한 서버와 연결되기 시작하고 서버에 로그인할 준비가 된다. 우리는 클라이언트의 이 상태를 ```CM_CONNECT``` 상태라고 한다.

##### 3.2 기본 서버에 로그인
CM 클라이언트는 다른 CM 노드들과 상호작용하기 위해서 기본 서버에 반드시 로그인 해야한다. 로그인 프로세스를 위해 CM 클라이언트 stub 은 ```loginCM``` 메소드를 제공한다. 이 메소드는 유저 이름과 비밀번호의 두 개의 파라미터를 받는다. 클라이언트가 이 메소드를 호출할 때 클라이언트 CM 은 기본 서버에 로그인을 요청한다. 아래 간단한 예제는 클라이언트 어플리케이션이 유저 이름과 비밀번호를 받아 기본 서버에 로그인을 요청하는 것을 보여준다.

서버 CM 이 로그인 요청을 받을 때 요청한 유저를 CM 서버 설정 파일 (cm-server.conf) 에 설정한 CM 로그인 방식에 따라서 인증을 수행한다.

###### 3.2.1 로그인 미사용
만약 서버 CM 이 ```LOGIN_SCHEME``` 필드를 0 으로 설정하면 유저의 인증을 하지않고 모든 로그인 요청을 받아들여 요청한 클라이언트는 항상 기본 서버 로그인에 성공한다. 그러므로 서버와 클라이언트 어플리케이션은 로그인 요청 후에 아무것도 할 필요가 없다.

###### 3.2.2 로그인 방식 사용
만약 서버 CM 이 ```LOGIN_SCHEME``` 필드를 1 로 설정하면 서버 어플리케이션은 요청한 유저를 자신의 인증 정책에 따라 인증을 수행할 책임이 있다. 이것을 위해 서버 어플리케이션은 이벤트 핸들러에 로그인 요청 이벤트 (```CMSessionEvent.LOGIN```) 을 캐치해야하고 유저 인증을 수행 후 인증 결과를 유저에게 알려준다. 아루 예제는 인증을 위한 CM DB 관리 모듈을 사용하는 인증 루틴을 보여준다. 인증 코드는 이전 예제의 서버 이벤트의 ```processSessionEvent``` 메소드에 추가되었다.

CM DB 매니저를 사용한 유저 인증을 위해 서버 어플리케이션은 주어진 유저 이름이 CM DB 에 등록되어있는지, 주어진 비밀번호가 올바른지 확인하는 ```authenticateUser``` 메소드를 호출한다. 이 메소드의 리턴 값은 ```Boolean``` 타입이다. CM DB 매니저의 자세한 사용법은 다른 섹션에 나와있다. 물론 서버 어플리케이션은 CM DB 매니저 대신에 다른 인증 방법을 사용할 수 있다. 서버 어플리케이션에 의해 어떤 인증 방법이 사용되던지 CM 상호작용 매니저의 ```replyToLOGIN``` 메소드를 호출함으로서 CM 에게 주어지는 인증 결과와 무관하다. ```replyToLOGIN``` 메소드는 3 가지 파라미터를 가진다. 첫번째는 받은 로그인 요청 이벤트이다. 두번째 파라미터는 요청한 유저가 유효한 유저인지 알려주는 ```Boolean``` 값이다. 만약 이 변수가 ```true```이면 유저는 유효한 유저이다. 그렇지 않으면 유효하지 않은 유저이다. 마지막 파라미터는 CM 의 내 사용에 필요한 모든 정보를 저장하는 CM 정보 객체에 대한 참조이다. 만약 서버 어플리케이션이 ```replyToLOGIN``` 메소드를 호출하면 서버 CM 은 로그인 요청에 대한 응답 이벤트 (```CMSessionEvent.LOGIN_ACK```)를 만들고 요청한 클라이언트에게 보낸다.

로그인 요청이 성공적인지 아닌지 체크하기 위해 클라이언트 이벤트 핸들러는 ```LOGIN_ACK``` 이벤트를 캐치해야 한다. 아래 예제는 어떻게 클라이언트 이벤ㄴ트 핸들러가 로그인 요청의 결과를 아는지 보여준다. ```LOGIN_ACK``` 이벤트 에 정수 타입의 결과 필드를 세팅하고 ```isValidUser``` 메소드에 의해 값은 검색된다. 만약 값이 1 이면 로그인 요청이 성공적으로 완료되고 요청한 클라이언트는 ```CM_LOGIN``` 상태가 된다. 그렇지 않으면 로그인 처리는 실패한다.

이벤트 ```LOGIN_ACK``` 의 자세한 정보는 아래와 같다.

###### 3.2.3 다른 유저들의 로그인 알림
서버 CM 이 클라이언트로부터 로그인 요청을 받을 때 서버 CM 은 ```SESSION_ADD_USER``` 이벤트로 로그인 유저의 정보를 다른 참가한 클라이언트들에게 알려준다. 클라이언트 어플리케이션은 이런 정보를 사용하길 원하면 이벤트 핸들러 루틴 안에서 이 이벤트를 캐치할 수 있다. ```SESSSION_ADD_USER``` 이벤트의 자세한 정보는 아래와 같다.

##### 3.3 세션 참여
로그인 프로세스가 끝난 뒤 클라이언트 어플리케이션은 반드시 세션에 참여하고 CM 네트워크에 들어가는 것을 마치기 위해 CM 의 그룹에 들어가야 한다. 세션에 참여하는 과정은 CM 서버 설정 파일에서 서버 CM 이 단일 세션을 적용하는지 다중 세션을 적용하는지에 따라 다르다. 만약 클라이언트가 세션에 참여하면 클라이언트 CM 은 자동으로 세션의 기본 그룹에 참여를 진행한다.

###### 3.3.1 단일 세션
만약 서버 설정 파일의 ```SESSION_SCHEME``` 필드가 0 이면 서버 어플리케이션은 단일 세션을 사용한다. 단일 세션 정보와 그 안의 최소 하나의 그룹 또한 설정되어야 한다. 이 경우 클라이언트 CM 은 자동으로 로그인 요청이 끝나자마자 세션 참여를 요청한다. 그러므로 클라이언트 어플리케이션은 세션에 참가하기 위해 아무것도 하지 않는다.

###### 3.3.2 다중 세션
만약 서버 설정 파일의 ```SESSION_SCHEME``` 필드가 1 이면 서버 어플리케이션은 클라이언트가 여러 세션 중 참여할 것을 고를 수 있도록 다중 세션을 지원한다. 개발자는 서버 설정 파일에 다중 세션을 설정해야 하고 분리된 세션 설정 파일의 각 세션에 최소 하나의 그룹을 설정해야 한다. 로그인 처리 후 클라이언트 어플리케이션은 서버에서 얼마나 많은 세션들이 제공되는지 모른다. 그러므로 클라이언트는 세션 정보를 요청해야 하고 한 세션을 선택해 세션에 참여를 요청해야 한다.

###### 3.3.3 세션 정보 요청
기본 서버에 세션 정보를 요청하기 위해 클라이언트 어플리케이션은 단순히 CM 클라이언트 stub 의 ```requestSessionInfo``` 메소드를 호출할 수 있다. 서버 CM 이 이 요청을 받으면 세션 이름, 서버 주소, 서버 포트 번호, 현제 세션 멤버의 수와 같은 이용 가능한 세션 정보를 포함한 응답 이벤트를 만들어 보낸다. 클라이언트 어플리케이션은 아래 예제에서 볼 수 있듯이 클라이언트 이벤트 핸들러에서 세션 정보를 받을 수 있다. 이 예제는 전체 이벤트 핸들러 클래스의 관련된 부분만 보여준다.

<code>requestSessionInfo</code> 메소드의 응답 이벤트는 ```CMSessionEvent``` 클래스의 ```RESPONSE_SESSION_INFO``` 이다. 이 이벤트는 자바 벡터 객체로 세션 정보 리스트를 포함한다. 이 벡터의 참조는 응답 이벤트의 ```getSessionInfoList``` 메소드로 검색된다. 각 벡터 요소는 세션 이름, 서버 주소, 서버 포트 번호, 현재 세션 멤버의 수와 같은 세션 정보를 포함하는 ```CMSessionInfo``` 클래스를 참조한다. 위의 예제의 결과는 아래 그림과 같다.

<code>RESPONSE_SESSION_INFO</code> 이벤트의 자세한 정보는 아래와 같다.

###### 3.3.4 세션 참여 요청
만약 CM 서버가 다중 세션을 제공하면 클라이언트 어플리케이션은 기본 서버로부터 세션 정보를 요청하고 받은 후에만 세션에 참여할 수 있다. 세션에 참여하기 위해 클라이언트 어플리케이션은 클라이언트 stub 의 ```joinSession``` 메소드를 호출한다. ```joinSession``` 메소드는 클라이언트가 참여할 세션의 이름인 하나의 파라미터만 받는다. 아래 코드는 유저에게서 주어진 이름의 세션에 참여하는 예제이다.

만약 클라이언트 CM 이 세션에 참여하는 것이 끝나면 ```CM_SESSION_JOIN``` 상태가 되고 자동으로 세션의 첫번째 그룹에 들어가게 된다. 예를 들어 만약 클라이언트가 앞에서 말한 서버 설정 파일의 *session1* 에 참여하면 *session1* 의 첫번째 그룹인 *g1* 에 들어가게 된다.

만약 서버가 그룹에 들어가면 드디어 다른 CM 노드들과 상호작용할 준비가 된 것이다.

###### 3.3.5 다른 유저들의 세션 참여 알림
서버 CM 이 클라이언트로부터의 세션 참여 요청을 완료하면 서버 CM 은 다른 참여한 클라이언트들에게 ```CHANGE_SESSION``` 이벤트로 새로운 세션 유저의 정보를 알려준다. 클라이언트 어플리케이션은 이런 정보를 사용하기 원한다면 이벤트 핸들러 루틴에서 이 이벤트를 캐치할 수 있다. ```CHANGE_SESSION``` 이벤트의 자세한 정보는 아래와 같다.

###### 3.3.6 다른 유저들의 그룹 참여 알림
서버 CM 이 클라이언트로부터의 그룹 참여 요청을 완료하면 서버 CM 은 다른 참여한 클라이언트들에게 ```NEW_USER``` 이벤트로 새로운 그룹 유저의 정보를 알려준다. 클라이언트 CM 이 이 이벤트를 받으면 나중에 현재 그룹 멤버들을 알 수 있도록 하기 위해 새로운 그룹의 정보를 저장한다. 클라이언트 어플리케이션은 이런 정보를 사용하기 원한다면 이벤트 핸들러 루틴에서 이 이벤트를 캐치할 수 있다. ```NEW_USER``` 이벤트의 자세한 정보는 아래와 같다.

###### 3.3.7 기존 그룹 멤버에 대한 알림
서버 CM 이 클라이언트로부터의 그룹 참여 요청을 완료하면 서버 CM 은 ```INHABITANT``` 이벤트 시리즈로 기존 그룹의 유저들의 정보를 새로운 유저에게 알려준다. 클라이언트 CM 이 이 이벤트를 받으면 나중에 현재 그룹 멤버들을 알 수 있도록 하기 위해 기존 그룹 유저의 정보를 저장한다. 클라이언트 어플리케이션은 이런 정보를 사용하기 원한다면 이벤트 핸들러 루틴에서 이 이벤트를 캐치할 수 있다. ```INHABITANT``` 이벤트의 자세한 정보는 아래와 같다.

#### 4. CM 네트워크 떠나기
개발자는 클라이언트 어플리케이션이 다른 CM 노드와 상호작용 하기 위해 명명된 순서대로 초기화, 로그인, 세션 참여, 그룹 참여 과정을 필요로 하는 것을 알아야 한다. 이러한 과정들은 클라이언트의 상태 변화를 초래한다.

<code>CM_INIT -> CM_CONNECT -> CM_LOGIN -> CM_SESSION_JOIN</code>

서버 CM 은 이런 상태가 없음을 알아두자. 클라이언트가 그룹과 세션에 참여하는 ```CM_SESSION_JOIN``` 상태로부터 클라이언트는 아래 설명되는 것처럼 어떤 이전 상태로든 변할 수 있다.

##### 4.1 현재 세션 떠나기
현재 세션을 떠나기 위해 클라이언트는 아래 예제와 같이 CM 클라이언트 stub 의 ```leaveSession``` 메소드를 호출할 수 있다. 이 메소드는 먼저 클라이언트가 현재 그룹과 세션을 떠나게 한다. 클라이언트가 세션을 떠나면 ```CM_LOGIN``` 상태로 바뀌고 더 이상 다른 CM 노드들과 통신할 수 없다. 세션에 다시 참여하기 위해 클라이언트는 세션 이름 파라미터와 함께 ```joinSession``` 메소드를 반드시 호출해야 한다.

###### 4.1.1 다른 유저들이 세션을 나간 것에 대한 알림
서버 CM 이 클라이언트로부터의 세션을 나가는 요청을 완료하면 서버 CM 은 ```CHANGE_SESSION``` 이벤트로 유저가 떠난 정보를 다른 참여한 클라이언트들에게 알려준다. 클라이언트 어플리케이션은 이런 정보를 사용하기 원한다면 이벤트 핸들러 루틴에서 이 이벤트를 캐치할 수 있다. 만약 이 이벤트의 세션 이름 필드가 비었다면 클라이언트는 유저가 그/그녀의 현재 세션을 떠난 것을 알 수 있다.

###### 4.1.2 다른 유저들이 그룹을 나간것에 대한 알림
서버 CM 이 클라이언트로부터의 그룹을 나가는 요청을 완료하면 서버 CM 은 ```REMOVE_USER``` 이벤트로 그룹 유저가 나간 정보를 다른 참여한 클라이언트들에게 알려준다. 클라이언트 CM 이 이 이벤트를 받으면 그룹을 나간 유저의 정보를 삭제한다. 클라이언트 어플리케이션은 이런 정보를 원하면 이벤트 핸들러 루틴에서 이 이벤트를 캐치할 수 있다. ```REMOVE_USER``` 이벤트의 자세한 정보는 아래와 같다.

##### 4.2 기본 서버의 로그아웃
기본 서버에서 로그아웃 하기 위해 클라이언트는 아래 예제에서 볼 수 있듯이 CM 클라이언트 stub 의 ```logoutCM``` 메소드를 호출할 수 있다. 만약 클라이언트가 ```CM_SESSION_JOIN``` 상태에 있다면 이 메소드는 먼저 클라이언트가 세션을 떠나게 한다. 클라이언트가 기본 서버에서 로그아웃하면 ```CM_CONNECT``` 상태가 되고 여전히 기본 서버와 연결을 유지한다. 만약 클라이언트가 다시 CM 네트워크에 들어오고 싶다면 로그인, 세션 참여, 그룹 참여 과정을 거쳐야 한다.

###### 4.2.1 다른 유저들의 로그아웃에 대한 알림
서버 CM 이 클라이언트로부터의 로그아웃 요청을 완료하면 서버 CM 은 ```SESSION_REMOVE_USER``` 이벤트로 로그아웃한 유저의 정보를 다른 참여한 클라이언트들에게 알려준다. 클라이언트 어플리케이션은 이런 정보를 원한다면 이벤트 핸들러 루틴에서 이 이벤트를 캐치할 수 있다. ```SESSION_REMOVE_USER``` 이벤트의 자세한 정보는 아래와 같다.

##### 4.3 기본 서버로부터의 연결 종료
기본 서버로부터 연결을 끊기 위해서 클라이언트는 아래 예제에서 볼 수 있듯이 CM 클라이언트 sutb 의 ```disconnectFromServer``` 메소드를 호출할 수 있다. 먼저 이 메소드는 만약 클라이언트가 해당하는 상태에 있다면 클라이언트가 현재 그룹과 세션을 떠나게 하고 기본 서버에서 로그아웃하게 한다. 마지막으로 클라이언트는 기본 서버와 연결을 끊고 ```CM_INIT``` 상태가 된다. 만약 클라이언트가 다시 CM 네트워크에 들어오고 싶다면 CM 클라이언트 stub 의 ```connectToServer``` 메소드를 호출해 기본 서버와 연결하고 전과 같이 로그인, 세션 참여, 그룹 참여 과정을 따라야한다.

##### 4.4 CM 종료
만약 클라이언트나 서버 어플리케이션이 CM 을 사용하지 않고 이를 종료하고 싶다면 아래 예제에서 볼 수 있듯이 CM stub 의 ```terminateCM``` 메소드를 호출할 수 있다. 이 메소드는 ```startCM``` 메소드의 반대이고 CM 의 실행을 마친다. 클라이언트 어플리케이션의 경우 종료하기 전에 클라이언트는 나머지 CM 네트워크를 일관된 상태로 유지하기 위해서 현재 그룹과 세션을 떠나고 로그 아웃하고 기본 서버와 연결을 종료한다. 뿐만 아니라 서버나 클라이언트 어플리케이션이 의도치 않게 꺼지면 다른 남은 CM 노드들은 예상치 못한 연결 종료를 알아차리고 종료된 CM 노드의 정보를 삭제한다.

#### 5. 현재 그룹의 변화
만약 세션이 여러 그룹들을 가지고 있다면 클라이언트는 세션에 참여하고 첫번째 그룹에 참여한다. 클라이언트는 CM 클라이언트 stub 의 ```changeGroup``` 메소드를 호출해 현재 그룹을 바꿀 수 있다. 이 메소드의 파라미터는 클라이언트가 참여하길 원하는 타겟 그룹의 이름이다. 타겟 그룹 이름은 반드시 서버 어플리케이션의 세션의 설정 파일에 존재해야 한다. 그러면 클라이언트는 현재 그룹을 떠나고 새로운 타겟 그룹에 참여한다. 아래 코드는 유저로부터 주어진 이름의 그룹으로 변경하는 예제이다.

## 전석규
5.Changing current group
====================================
세션 - 다수 그룹
--> 사용자 세션 조인시 1번째 그룹에 조인

그룹 바꾸기 --> mClientStub.changeGroup(strGroupName)

(단, 그룹 이름은 configuration file 에 있어야함)

6. A chatting event
=================================

method --> chat
param : str target, str message
         --> /b : to all login users
			 /s : to session members
		     /g : to group members
			 /'name' : to user 'name' ( if 'name'이 SERVER면 default 서버로 보내짐)


---------------------
6.1 Receiving a chatting event

CM 어플리케이션은 채팅이벤트를 사전에 정의한 EVENt handler의 CM EVent형태로 수신 가능

CM chat event
- 1. SESSION_TALK Event (CMSessionEvent class)
	default server에 로그인했을때

	getUserName() : 보낸 유저의 이름
	getTalk() : 채팅 메시지
	getHandlerSession() : 보낸 유저의 현재 세션

- 2. USER_TALK 이벤트 (CMInterestEvent Class)
	그룹에 조인해있을떄

	getHandlerGroup() : 보낸 유저의 현재 그룹


7. Sending/Receiving a CM Event
=================================
7.1 Creating an Event
---
CM은 나가고 들어오는 메시지를 CM Event형태로 처리한다.

CM_DUMMY_EVENT
		setHandlerSession() : 보내는 유저의 현재 세션을 기입
		setHandlerGroup() : 보내는 유저의 현재 그룹을 기입
		setDummyInfo() : 메시지 정보를 기입

CM Stub 객체의 cast 함수를 통해서 이벤트를 cast할 수 있다


CM에는 전체 내부 정보는 CMInfo 클래스에 들어있고,유저 정보는 user object에 들어있따.
CMInfo 클래스는 CM stub 객체의 getCMInfo 함수를 통해 얻을 수 있다.

CMInfo 클래스는 CMInteractionInfo 클래스를 포함하고 있는데,
클라이언트의 경우 해당 클래스는 유저 정보와, default 서버 정보와 같은 global 정보를 가지고 있따.

CMInteractionInfo 객체는 CMInfo 클래스에서 getInteractionInfo함수를 호출해 사용한다

user 정보는 CMInteractionInfo 클래스의 getMySelf 메소드를 통해 얻을 수 있따.

7.2 Sending a CM event
---
transmission mode : one-to-one , one-to-many, one-to-all

각각 send, cast(multicast), broadcast함수이다

7.2.1
---
boolean send(CMEvent cme, String strTarget)

strTarget : 서버 이름 혹은 유저 이름. default server이면 SERVER

7.2.2
---
boolean cast(CMEvent cme, String sessionName, String groupName)

sessionName만 쓰면 : 세션의 모든 유저에게
둘다 쓰면 : 그룹의 모든 유저에게
둘다 없으면 : default server에 로그인된 모든 유저에게

7.2.3
---
boolean multicast(CMEvent cme, String sessionName, String groupName)

서버의 설정 파일의 Communication architecture가 CM_PS인경우에 사용 가능

cast와는 다르게,  sessionName과 groupName이 필수


7.2.4
---
boolean broadcast(CMEvent cme)


7.2.5
---
- Transmission reliability

send, cast, broadcast에 int opt를 추가해서 Transmission method를 바꿀 수 있다.

0이면 TCP, 1이면 UDP

- Multiple communication channels

int nChNum을 추가해서, 특정 채널을 통해 가도록 할 수 있다.

CM은 내부적으로 모든 채널을 저장한다.

default 채널은 0이고, 추가적인 채널의 인덱스는 이보다 커야한다.

- Blocking/Nonblocking communication channels

boolean isBlock param을 추가해 blocking channel/non-blocking channel을 선택할 수 있다

default는 non-blocking communication channel을 이용한다


7.3 Receiving a CM Event
---

Event를 수신하기 위해선, CM Stub에 event handler 객체를 설정해놔야 한다.

사전에 정의된 CM event를 포착하면,
Handler는 getType, getID 메소드를 통해 이벤트를 식별한다

하지만, CMDummyEvent의 경우 event type으로만 식별된다 ( 개발자에 의해만들어졌기 떄문 )

getHandlerSession() : 보낸 유저의 현재 세션
getHandlerGroup() : 보낸 유저의 현재 그룹
getDummyInfo() : 수신한 메시지



8. CM User Event

CMUserEvent를 통해, user-defined event를 보다 유연한 포맷으로 사용할 수 있따

CMUserEvent는 identifier 문자열을 통해 식별된다.

id,eventfield,eventbytesfield 의 getter,setter가 구현되어있따

setEventField(int type, String fName, String fValue)
--> type은 CMInfo.CM_INT, LONG, FLOAT, DOUBLE, CHAR, STR이 있다
--> fName은 이벤트를 이름으로 식별하는데 쓰인다
--> value는 항상 String 타입으로 주어져야한다

setEventBytesField 메소드를 통해, byte stream을 지정할 수 있다
--> fName : 이름 식별자
--> int byteNum : 바이트스트림 크기
--> byte[] bytes : 바이트스트림


9. Management of additional communication channels

CM 처음 시작시에, default TCP / UDP communication 채널을 쓸 수 있따

communication Architecture가 CM_PS일떄, 어플리캐이션은 그룹에 예속된 default multicast channel을 쓸 수 있다.


9.1 추가적인 stream (TCP) channel
----

client CM만이 TCP 채널을 추가하고 제거할 수 있다.

CM client stub에 메소드로 구현되어있따

boolean addNonBlockSocketChannel(int nChKey, String strServer)
- non-Blocking Socket channel을 서버로 추가한다
- valid socket channel의 reference를 client에게 준다( 하지만 서버가 relevant channel information 을 추가하기 전에는 불안전하다)
- 클라이언트가 ADD_NONBLOCK_SOCKET_CHANNEL_ACK 이벤트 리턴코드 1로 수신하면 채널 개설이 완료된다. 해당 이벤트는 event Handler로 확인 가능

SocketChannel syncAddNonBlockSocketChannel(int nChKey, String strServer)
- non-Blocking Socket 울 synchronous하게 추가한다.

boolean addBlockSocketChannel(int nChKey, String strServer)
- blocking socket channel을 서버에 추가한다. 이때 nCHKey 파라미터는 0도 가능하다.
ADD_BLOCK_SOCKET_CHANNEL_ACK 를 이벤트 핸들러로 처리하면된다.

SocketChannel syncAddBlockSocketChannel(int nChKey, String strServer)
- blocking socket channel을 synchronous하게 추가한다.

param : nChKey( 0보다 큰 채널 키 ) / strServer : 서버이름 (default서버이름 : 'SERVER')

TCP채널 제거하기 위해선 클라이언트에서 다음과 같은 함수 콜하면된다.

boolean removeNonBlockSocketChannel(int nChKey, String strServer)
- non-blocking socket channel을 서버에서 제거한다.

boolean removeBlockSocketChannel(int nChKey, String strServer)
- blocking socket channel을 제거한다. 클라이언트가 ack 이벤트를 받았을때 제거된다
	REMOVE_BLOCK_SOCKET_CHANNEL_ACK

boolean syncRemoveBlockSocketChannel(int nChKey, String strServer)
- blocking socket channel을 synchronously하게 제거한다



9.2 Additional datagram (UDP) channels
---
void addDatagramChannel(int nChPort)
- UDP채널을 추가한다. 새 채널의 port 번호가 파라미터

void removeAdditionalDatagramChannel(int nChPort)
- UDP채널을 제거한다


9.3 Additional multicast channels
---
void addMulticastChannel(String strSession, String strGroup, String strChAddress,
int nChPort)
- MultiCast Channel을 추가한다.
strSession, strGroup : Channel이 assign될 세션과 그룹 이름
strChAddress : multicast address (unique) / nChPort : 포트 번호 (unique)

void removeAdditionalMulticastChannel(String strSession, String strGroup)
- MulticastChannel을 제거한다


10.Current client imformation
---
CM Client측에서 현재 client의 상태와 정보에 대해 접근할 수 있다.

CM stub module.getCMInfo() --> CMInfo.getInteractionInfo() --> CMInteractionInfo.getMyself()
--> CMUser class에서 접근 가능하다

CMUser.getName() : 이름
CMUser.getHost() : host adress
CMUser.getUDPPort() : Default UDP port number
CMUser.getCurrentSession() : session name
CMUser.getCurrentGroup() : group name
CMUser.getState() : connection state to the default server
--> 1: CM_INIT / 2: CM_CONNECT / 3: CM_LOGIN / 4: SESSION_JOIN



## 오민탁 (11~13)

11. 파일 전송 매니지먼트

CM 애플리케이션은 직접적인 연결을 통해 파일을 교환한다. 각각은 CMClientStub와 CMSeverStub 클래스의 부모가 되는 CMStub 클래스이다. 클라이언트 - 서버 구조에서 클라이언트는 서버와 파일을 주고 받을 수 있다. 애플리케이션에 의해 CM이 초기화되었을 때, 디폴트 디렉토리가 configuration 파일 안에 있는 path 정보로 설정이 된다. 디폴트 디렉토리가 없으면, CM이 만들어준다. 만약 FILE_PATH가 설정이 안되있으면, 디폴트 path는 현재 작업 디렉토리로 설정된다.

만약 파일 전송이 요청되면, sender는 디폴트 파일 path에서 요청된 파일을 검색한다. 만약 클라이언트가 이 파일을 받으면, CM은 이 파일 path안에 있는 파일을 저장한다. 만약 서버가 파일을 받으면, CM은 디폴트 path의 하위 디렉토리에 파일을 저장한다. 하위 디렉토리 이름은 클라이언트(sender) 이름이다.

CM 애플리케이션은 CMStub에 있는 setFilePath로 디폴트 파일 path를 변경할 수 있다. 이 method의 개요는 아래와 같다.

`void setFilePath(String strFilePath)`

parameter는 새로운 디폴트 디렉토리 정보이다.

CM 파일 전송 매니저는 push와 pull모드 두가지 전송 모드를 지원한다.
<br><br><br>
11.1 파일 요청(pulling) 받기

pull모드에서 CM 애플리케이션은 파일을 다른 원격지 CM 애릎리케이션에 요청한다. 예를 들어, CM 서버가 보낸 파일을 한 CM 클라이언트가 요청할 수 있다. CMStub 클래스의 requestFile method에 의해 파일이 요청되고, 개요는 아래와 같다.

`boolean requestFile(String strFileName, String strFileOwner)`

`boolean requestFile(String strFileName, String strFileOwner, byte byteFileAppend)`

첫번째 requestFile method는 두개의 parameter를 요구한다. 첫번째는 요구하는 파일의 이름이다. 두번째는 파일 소유주의 이름이다.
두번째 requestFile method는 세개의 parameter를 요구한다. 첫번째와 두번째는 위와 동일하고, 마지막 parameter인 byteFileAppend는 요청된 파일의 전체나 일부가 이미 있는 경우 수신자의 행동을 지정하는 파일 수신 모드이다. CM은 default(-1), overwrite(0), append(1) 모드 세가지 수신 모드를 제공한다. byteFileAppend parameter는 이들 세 모드중 하나가 될 수 있다. 만약 byteFileAppend가 default 모드이면, 파일 수신 모드가 CM configuration 파일의 FILE_APPEND_SCHEME에 의해 결정된다. 만약 byteFileAppend가 default가 아닌 두개중 하나이면, 요청된 파일의 파일 수신 모드는 CM configuration 파일을 따르지 않고 이 parameter 값을 따른다. overwrite 모드는 이미 동일한 파일이 있어도 수신자가 항상 전체 파일을 overwrite한다. append 모드는 수신자가 파일 블록을 건너 뛰고 나머지 블록만 수신한다.

만약 클라이언트가 파일을 요청하면, 파일 소유자는 서버가 될 수 있다. 만약 서버가 파일을 요청하면, 파일 소유자는 클라이언트가 될 수 있다.

만약 클라이언트가 파일을 디폴트 서버에 요청하고 그 서버가 파일 path안에 파일을 가지고 있으면, 클라이언트는 성공적으로 그 파일을 받고 파일 path에서 찾을 수 있다. 만약 요청받은 파일이 서버에 없으면, 서버는 요청의 응답으로 사전에 정의된 파일 이벤트를 보내고 파일 전송 프로토콜을 끝낸다. REPLY_FILE_TRANSFER라는 응답 파일 이벤트가 오면, 클라이언트는 요청된 파일이 서버에 있는지 없는지를 알아낼 수 있다. REPLY_FILE_TRANSFER 이벤트에서 요청된 파일이 있으면 return code가 1이되고 없으면 0이 된다.

요청된 파일이 완전히 전송이 되었을 때, sender CM은 END_FILE_TRANSFER 이벤트를 요청자에게 보낸다. 전체 파일이 전송될 때 요청한 사람이 이벤트를 수신해야 하는 경우 이벤트 핸들러에서 캐치할 수 있다. END_FILE_TRANSFER 이벤트는 전송이 성공하면 return code로 1을 주고 실패하면 0을 준다.
<br><br><br>
11.2 파일 보내기(pushing)

pull 모드와 달리 push 모드에서 CM 애플리케이션은 다른 원격지 CM 애플리케이션에 파일을 보낼 수 있다. 보낸사람 애플리케이션에서 선택한 파일이 있는 경우 그 파일을 CMStub 클래스의 pushFile method에 의해 push할 수 있다. 이 method는 파일 push가 성공적으로 받는사람에게 알려졌을 때 true를 반환한다.

`void pushFile(String strFilePath, String strReceiver)`

requestFile method처럼, pushFile method는 두개의 parameter를 요구한다. 첫번째는 보낼 파일의 경로이다. 두번째는 받는 사람의 이름이다.

파일이 받는 사람에게 완전히 전송되었을 때, 받는 CM은 END_FILE_TRANSFER_ACK 이벤트를 전송한 사람에게 보낸다. 이 이벤트는 전체 파일이 성공적으로 받아지면 1을 return하고 수신 오류가 발생하면 0을 return 한다.
<br><br><br>
11.3 파일 전송 취소

진행중인 파일 전송은 CMStub 클래스의 cancelPushFIle이나 cancelRequestFile method에 의해 취소될 수 있다.

`boolean cancelPushFile(String strReceiver)`

`boolean cancelRequestFile(String strSender)`

cancelPushFile method는 받는사람에게 파일 보내는 것을 취소한다. 이 method를 통해 보내는 사람이 받는사람에게 보내는 작업을 전부 취소할 수 있다. 파일 보내는 작업은 서버 CM configuration 파일의 FILE_TRANSFER_SCHEME 의 결정에 상관없이 취소 될 수 있다. 취소가 성공하면 true를 반환한다. 취소는 리시버에게 알림을 준다. 리시버의 취소 결과는 sender에게 CANCEL_FILE_SEND_ACK 이나 CANCEL_FILE_SEND_CHAN_ACK 이벤트가 보내진다. 첫번째 이벤트는 파일 전송 서비스가 디폴트 채널을 사용할 때 보내지고, 두번째 이벤트는 파일 전송 서비스가 분리된 채널을 사용할 때 보내진다.

cancelRequestFile method는 보내는 사람으로부터 파일 받는것을 취소하는 것이다. 받는 사람은 이 method를 통해 보내는 사람으로부터 받는 작업을 모두 취소할 수 있다. 파일 보내는 작업 취소와는 다르게, 파일 받는 작업은 파일 전송 scheme이 분리된 채널을 사용할때만 취소가 가능하다.
<br><br><br>
11.4 네트워크 처리량 측정

CM 애플리케이션은 직접적으로 연결된 다른 CM 애플리케이션으로부터 들어오고 나가는 네트워크 처리량을 측정할 수 있다. CMStub 클래스에 있는 measureInputTroughput 이나 measureOutputThroughput method를 통해 들어오고 나가는 네트워크 처리량을 측정할 수 있다.

`float measureInputThoughput(String strTarget)`

`float measureOutputThroughput(String strTarget)`

두 method는 한개의 strTarget이라는 parameter를 요구한다. 네트워크 처리량이 성공적으로 측정되면, 이 method는 MBps의 단위로 측정값을 반환한다. 만약 오류가 나면 -1이 나온다.
<br><br><br>

12. 유저 관리

CM 은 또한 유저 관리 기능을 제공한다. 유저 관리는 등록, 등록 취소, 유저 검색을 포함한다. 유저 프로필 정보가 DB안에 저장되기 때문에, 개발자는 서버 configuration 파일을 통해 CM DB를 설정해야 한다. 유저 관리 API는 CM 클라이언트 stub 모듈에 의해 제공된다. 디폴트 서버는 CM 이벤트로써 요청의 결과를 보내준다.

12.1 유저 등록

유저가 CM 클라이언트 stub의 registerUser method를 통해 등록할 수 있다. 클라이언트가 디폴트 서버에 연결되어있으면, 이 method를 호출한다.

`void registerUser(String strName, String strPasswd)`

두개의 parameter를 요구한다. 유저 이름과 패스워드이다. CM BD에 똑같은 사용자 이름이 존재하면, 등록은 실패한다. 유저 이름은 유니크해야 등록에 성공한다.

등록 요청은 REGISTER_USER_ACK 이벤트를 return code로 준다. 성공하면 1, 실패하면 0을 리턴한다.

12.2 유저 등록 취소

CM 클라이언트 stub의 deregisterUser method를 통해 등록 취소를 할 수 있다. 클라이언트가 디폴트 서버에 연결되어 있으면, 이 method를 호출한다. 요청이 있으면, CM DB에서 등록된 유저 정보를 지운다.

`void registerUser(String strName, String strPasswd)`

registerUser method처럼 유저 이름과 패스워드가 필요하다. 주어진 유저 이름과 정확한 패스워드가 CM DB에 있으면 등록 취소가 성공한다. 등록 취소 요청은 DEREGISTER_USER_ACK 이벤트를 return code로 반환한다.

CMSessionEvent의 DEREGISTER_USER_ACK 이벤트를 요청한 클라이언트가 받을 때, 이 이벤트는 요청의 결과를 보여줄 수 있다.

12.3 유저 검색

CM 클라이언트 stub의 findRegisteredUser method를 통해 다른 유저를 검색할 수 있다. 요청이 있을 때, CM은 검색하는 타겟 유저의 이름과 등록 시기같은 기본 프로필을 제공해준다.

`void findRegisteredUser(String strName)`

주어진 유저 이름이 CM DB에 있으면, 검색 요청은 성공적으로 끝난다. 유저 검색이 요청되면 FIND_REGISTERED_USER_ACK 이벤트를 return code로 반환한다.
<br><br><br>

13. SNS 관리

CM을 사용하면서, 개발자는 친구관리나 SNS 콘텐츠를 업로드와 다운로드 같은 SNS 관련된 기능을 쉽게 수행할 수 있다. CM의 SNS 관리를 가능하게 하기 위해서, 개발자는 서버 configuration 파일의 CM DB를 사용해서 설정해야 한다. 이 기능들은 CM 클라이언트 stub의 API 로 제공되며, 클라이언트는 디폴트 서버에 요청해서 methods를 호출할 수 있다. 모든 SNS API를 사용하기 위해서, 클라이언트는 디폴트 서버에 로그인해야 한다. 디폴트 서버가 클라이언트로부터 SNS 관리 요청받을 때, CM SNS 이벤트같은 요청 결과를 클라이언트에게 보내주고, 클라이언트는 받은 이벤트를 캐치한 결과를 보여줄 수 있다.

13.1 친구 관리

SNS 애플리케이션에서, 유저의 친구 관리를 도와주는건 일반적이다. CM 은 클라이언트가 친구 정보를 얻고, 추가하고, 삭제하는 것을 쉽게 도와준다.

`void addNewFriend(String strFriendName)`

`void removeFriend(String strFriendName)`

`void requestFriendsList()`

`void requestFriendRequestersList()`

`void requestBiFriendsList()`

클라이언트는 addNewFriend method를 통해 CM에 등록되어있는 유저 이름이 있으면 친구로 추가할 수 있다. 디폴트 서버가 새로운 친구를 추가하는 요청을 받으면, 먼저 그 친구가 등록된 유저인지 아닌지 검사한다. 만약 등록된 유저라면, 서버는 CM DB의 친구 테이블에서 요청한 유저의 친구로 추가한다. 등록된 유저가 아니라면 요청은 실패한다. 서버는 요청 결과로 클라이언트에게 ADD_NEW_FRIEND_ACK 이벤트를 보낸다.

클라이언트는 removeFriend method를 통해 친구를 삭제할 수 있다. 디폴트 서버가 친구를 삭제하는 요청을 받았을 때, 요청한 유저의 친구를 찾는다. 만약 친구가 발견되면, 서버는 친구 테이블로부터 대응되는 엔트리를 삭제한다. 요청의 결과로 클라이언트에게 REMOVE_FRIEND_ACK 이벤트를 보낸다.

CM은 다른 유저 리스트를 요청하는 methods로 친구 관리의 다양한 policy를 지원한다. requestFriendsList method 는 요청하는 유저가 다른 사람의 동의없이 추가한 유저 목록을 요청한다. requestFriendRequestersList method는 요청하는 사람을 친구로 추가했지만, 그 사람은 아직 친구로 추가하지 않은 목록을 요청한다. requestBiFriendsList method는 요청한 유저를 친구로 추가하고 요청한 친구 역시 친구로 추가한 유저들의 목록을 요청한다.

디폴트 서버가 클라이언트로부터 친구, 요청한사람, 서로 친구인 사람에 대한 요청을 받으면, 대응하는 유저 목록을 RESPONSE_FRIEND_LIST, RESPONSE_FRIEND_REQUESTER_LIST, RESPONSE_BI_FRIEND_LIST 이벤트로서 클라이언트에게 보낸다. 친구 목록은 최대 50명의 유저 이름이 나타난다. 50명이 초과가 되면, 서버는 이벤트를 한번 이상 보낸다.

13.2 SNS 콘텐츠 관리

CM 의 SNS 콘텐츠 관리 기능을 사용하면서, 클라이언트 애플리케이션은 CM 디폴트 서버에 의해 관리되는 SNS 콘텐츠를 업로드하고 다운로드하는 요청을 할 수 있다. 콘텐츠를 업로드하고 다운로드하는 동안, 클라이언트는 CM 클라이언트 stub 에 있는 requestSNSContentUpload 와 requestSNSContent method를 호출할 수 있다. CM DB가 지속적으로 사용된다면, CM 디폴트 서버는 CM DB의 SNS 콘텐츠 테이블에 업로드된 콘텐츠를 저장한다. 디폴트 서버가 CM DB를 사용하지 않으면, 서버가 동작하는 동안 업로드 된 콘텐츠를 메모리에 저장한다. 그러므로 SNS 콘텐츠를 지속적으로 지원하려면 CM DB를 사용하는 것이 추천된다.

13.2.1 콘텐츠 업로드

`void requestSNSContentUpload(String user, String message, int nNumAttachedFiles, int nReplyOf, int nLevelOfDisclosure, ArrayList<String> filePathList)`

클라이언트는 디폴트 서버에 메시지를 업로드하기 위해서 이 method를 호출한다. 첫번째는 메시지를 업로드할 유저 이름이다. 두번째는 텍스트 메시지이다. 세번째는 이 메시지에 붙어있는 파일의 숫자이다. 마지막 parameter의 path 목록에 주어진 숫자와 동일해야 한다. 네번째는 이 메시지 응답하는 콘텐츠의 ID를 나타낸다. 만약 이 값이 0 이면, 업로드된 콘텐츠가 reply가 아니라 원래 내용이다. 다섯번째는 업로드된 콘텐츠의 공개 수준을 지정한다. CM은 콘텐츠의 공개수준을 0부터 3까지 4가지로 제공한다. 0은 업로드된 콘텐츠를 public 으로 공개한다. 1은 업로드된 콘텐츠를 업로드한 유저의 친구로 추가된 유저만 허용한다. 2는 업로드 된 콘텐츠에 업로드한 유저와 쌍방향 친구인 유저가 접근할 수 있다. 3은 private로 만들어 열수 없게 한다.

콘텐츠 업로드 요청이 서버에 오면, 유저 이름, 콘텐츠 ID, 업로드 시간, 첨부파일 숫자, reply ID, 공개 수준을 보함해서 요청한 메시지를 저장한다. 콘텐츠에 첨부파일이 있으면, 클라이언트는 서버에 그들을 분리해서 전송한다. 업로드 작업이 끝난 후에, 서버는 CONTENT_UPLOAD_RESPONSE 이벤트를 클라이언트에게 보내고 그 클라이언트는 요청의 결과를 확인할 수 있다.

13.2.2 콘텐츠 다운로드

클라이언트는 requestSNSContent method를 호출함으로써 콘텐츠를 다운로드 할 수 있다.

`void requestSNSContent(String strWriter, int nOffset)`

첫번째 parameter는 업로드된 콘텐츠의 유저이다. 클라이언트는 특정 작성자나 친구 그룹을 가리킬 수 있다. 'CM_MY_FRIEND'이면, 클라이언트는 요청자의 친구에 의해 업로드된 콘텐츠를 다운받는다. "CM_BI_FRIEND"이면, 클라이언트는 요청자의 쌍방향 친구에 의해 업로드된 콘텐츠를 다운받는다. strWriter가 빈 string이면, 클라이언트는 작성자의 이름을 지정하지 않고 접근 가능한 요청자의 모든 콘텐츠를 다운받는다. 마지막 parameter 'nOffset'은 요청된 콘텐츠 리스트의 시작으로부터 offset이다. 이는, 클라이언트가 최근 콘텐츠 중 nOffset-번째로부터 시작되는 SNS 메시지를 다운로드하고 싶을때 쓴다.

서버가 다운로드 요청을 받았을 때, 먼저 얼마나 많은 SNS 메시지가 보내질지 정해진다. 메시지의 숫자는 서버 configuration 파일의 DOWNLOAD_SCHEME 에 의해 결정된다. 이 숫자가 0이면, 서버는 요청 당 메시지의 숫자를 최대로 고정한다. 그리고 configuration 파일의 DOWNLOAD_NUM 의 값을 결정한다. DOWNLOAD_SCHEME 이 1이면, 서버는 동적 다운로드를 사용한다. 서버와 클라이언트 사이의 왕복 딜레이에 의한 다운로드 메시지 숫자를 정한다.

대부분의 경우에, 서버는 SNS 메시지 숫자에 대응되기 때문에 multiple CONTENT_DOWNLOAD 이벤트를 보낸다. 그리고 현재 다운로드의 끝 신호로서 CONTENT_DOWNLOAD_END 이벤트를 보낸다.
<br><br><br>
## 유승현


14. MySQL Server 구축 및 CM DB 관리.

 14.1 MySQL Server & Workbench 구축

 1. workbench는 새로운 스키마를 만들거나 CM DB table data를 import 할 때 사용.

 2. 다운로드 URL ( https://dev.mysql.com/downloads/mysql/ )

 3. version ( server 5.7   /   workbench 6.3)<br><br>



 14.2 실행 방법(pdf의 이미지를 보면서 하면 쉬울듯)

 - 먼저 설치된 MySQL server 실행.

 1. Workbench 실행

 2. default local connection 클릭 후 MySQL의 root passwd 입력

 3. 스키마 생성

     3_1) create a new schema 아이콘 클릭

     3_2) 스키마 이름을 "cmdb"로 할것.(CM서버의 구성파일인 cm-server.conf에 cmdb로 set 된다.)

     3_3) Apply 클릭

     3_4) "cmdb"스키마가 Workbench의 왼쪽 SCHEMAS 카테고리에 생성된 것을 확인.


 4. CM DB에 연결할 때 사용할 새로운 유저 추가

     4_1) Workbench 왼쪽의 MANAGEMENT 카테고리의 "User and Privileges" 클릭, 후 root passwd 입력

     4_2) "Add Account" 버튼 클릭 후 아래의 내용(새 유저의 정보) 입력

       - Login name : 유저이름(ex. ccslab)

       - Limits to Hosts Matching : % (DB에 어떤 host든 접근가능하게 하기 위해 입력한다고 한다.)

       - Password : (ex.ccslab)

       cf. 위의 유저이름, 비밀번호 정보는 cm-server.conf 파일에 저장됨.

     4_3) "Administrative Roles" 탭 클릭(여기서 role을 체크해야 하는데 사진에는 다 체크되어있음)


 5. 다른 유저들을 같은 이름으로 추가한다.

     5_1) 4의 과정을 반복 하지만, "Limits to Hosts Matching" 값을 "localhost"로 할것!!

     5_2) 같은 이름의 유저가 생성되었지만 다른 호스트(%, localhost)로 생성된 것 확인.


 6. Import CM DB table data

     6_1) MANAGEMENT 카테고리의 "Data Import/Restore" 클릭

     6_2) "Import from Self-Contained File" 옵션에 체크한 후,

          파일 선택 창에서 CMTest 프로젝트의 "SQL-dump" 디렉토리의 "Dump20160222.sql" 선택

     6_3) "Default Target Schema" 옵션에 이전에 만든 "cmdb" 선택 후 Start Import 클릭.

     6_4) 메인화면 왼쪽의 SCHEMAS 카테고리의 cmdb아래에 CM DB tables data가 잘 import되었는지 확인.



 7. DB connection 정보를 set up 한다.

     7_1) CM 폴더(git에서 받은 폴더)안의 cm-server.conf를 보면

         DB_USE 1
         DB_HOST localhost
         DB_USER ccslab
         DB_PASS ccslab
         DB_PORT 3306
         DB_NAME cmdb

       가 있음.
       DB_USE 가 1이면 DB를 사용한다는 것. 0이면 사용 안함.
       DB_HOST는 한 컴퓨터에 server와 client를 만들 시 localhost로 두면 되고 server를 따로 두면
       server의 ip 주소를 적으면 된다.
       유저 이름 / 유저 패스워드 / 포트번호(default가 3306) / Scheme 이름 순서이다.

<br><br>
14.3 CM DB management

 CM에는 user profile, friend list, SNS content, attachment files와 같은 대량의 정보를 지속적으로 유지하기 위해 DB에 접근을 필요로 하는 기능들이 존재한다.앞서 소개했던(14장 이전) API들의 기능들은 CM DB가 user, friend, SNS content, Attached file 을 관리하는 4개의 테이블을 갖고 있으므로 동작한다.

1) User Profile table

 `seqNum userName password creationTime`

 유저 프로파일 테이블은 위와 같은 네가지 필드를 갖고 있다.seqNum은 자동으로 증가하며 중복이 없는 시퀀스 넘버를 뜻한다. creationTime은 유저가 등록된 날짜와 시간정보를 저장한다.


2) Friend Table

 `userName friendName`

 프렌드 테이블에서는 유저의 이름과 해당 유저의 친구 이름을 저장하는 필드 2개가 존재한다.


3) SNS Content table

 `seqNum creationTime userName textMessage numAttachedFiles replyOf levelOfDisclosure`

 7개의 필드를 갖으며 각 필드는 고유한 시퀀스 넘버, 해당 컨텐츠가 업로드 된 날짜 및 시간, 유저이름, 텍스트 메세지, 같이 들어온 파일의 수, 소스 컨텐츠의 시퀀스 넘버, 정보의 노출레벨(0~3) 정보를 갖고 있다.


4) Attached file table

 `seqNum contentID filePath fileName`

 4개의 필드를 갖으며 각 필드는 시퀀스 넘버, 관련된 컨텐츠 ID, 파일의 위치, 파일 이름 정보를 나타낸다.


 위에서 소개한 CM DB에서 제공하는 4가지 default 테이블 대신 다른 DB 테이블을 만들고 싶다면, CM DB에서 다른 테이블을 만들고 사용 할 수 있고 직접 DB 쿼리를 통해 user profile, friend list, SNS content를 다른 방식으로 관리할 수 있다. 이러한 CM에 대한 direct access를 지원하기 위해 CM은 CMDBManager 라는 class를 제공한다.(CMDBManager는 뒤에 설명이 나온다.)

<br><br><br>

14.3.1 DB 구성

 CM은 MySQL을 DBMS로 사용하며 DB는 server configuration file(즉, cm-server.conf)에 구현되어있다. DB_HOST, DB_USER, DB_PASS,DB_PORT, DB_NAME과 같은 필드와 해당 정보가 cm-server.conf에 포함되어있다.( 위에서 DB 첫 구성시에 넣어줬던 정보들이 들어가있다.) 서버 CM이 동작하기 시작하면 구성되어있는 DB에 연결이 되고 이를통해 서버 어플리케이션이 접근할 수 있도록 한다.

<br><br>
14.3.2 CMDBMananger Class


 CMDBManager Class 는 static utility class 이며 CM DB에 직접 접근하기 위한 함수들을 제공한다. 이 클래스를 통해 개발자는 3개의 디폴트 테이블(users, friends, SNS content) 뿐만 아니라 다른 테이블도 쿼리문으로 제어할 수 있다. CMDBManager class는 다음과 같은 3가지 메소드를 제공한다.

 `ResultSet sendSelectQuery(String strQuery, CMInfo cmInfo)`

 MYSQL의 쿼리 중 SELECT문을 사용하고 싶을 때 쓴다. strQuery에 해당하는 쿼리문을 작성하고 CMInfo 객체는 stub객체(CMServerStub cmStub = server.getServerStub() 로 생성)의 getCMInfo()함수를 통해 가져올 수 있다. SELECT 쿼리를 수행한 결과를 ResultSet 타입으로 반환한다. 또한 이 반환 결과는 CM의 CMInfo 객체 안의CMDBInfo 객체에도 저장이 된다. 따라서 서버 어플리케이션은 최근에 한 SELE

 `int sendUpdateQuery(String strQuery, CMInfo cmInfo)`

 UPDATE, INSERT, DELETE와 같은 구문을 실행시키기 위한 쿼리를 전송할 때 사용한다. 만약 리턴값이 1이면 해당 쿼리가 성공적으로 수행되었음을 나타내고 1이 아닐 경우는 실패했을 경우이다. CMInfo 객체는 stub객체(CMServerStub cmStub = server.getServerStub() 로 생성)의 getCMInfo()함수를 통해 가져올 수 있다.

 `long sendRowNumQuery(String table, CMInfo cmInfo)`

 테이블을 구성하는 행(row)의 총 수를 반환한다.


 CMDBManager의 모든 메소드들은 CMInfo class의 값을 파라메터로 필요로 하며 이 값은 CM의 내부 전체 정보를 포함하고 있다. 만약 개발자가 새로운 테이블을 DB에 추가시키면 이를 위의 sendSelectQuery()와 sendUpdateQuery() 함수들을 통해 직접 테이블을 관리할 수 있다.
 Default Table들(users, friends, SNS content)를 위해 CMDBManager는 다른 부가적인 메소드들을 지원한다.


<br><br>

14.3.3 User Profile table 관리

 유의 사항 - CMInfo 객체는 stub객체(CMServerStub cmStub = server.getServerStub() 로 생성)의 getCMInfo()함수를 통해 가져올 수 있다.



 `int queryInsertUser(String name, String password, CMInfo cmInfo)`

 유저 프로파일을 추가하는 함수. 유저 이름, 비밀번호와 CMInfo 객체를 넣는다. CMInfo 객체는 stub객체(CMServerStub cmStub = server.getServerStub() 로 생성)의 getCMInfo()함수를 통해 가져올 수 있다.


 `boolean authenticateUser(String strUserName, String strPassword, CMInfo cmInfo)`

 사용자 인증을 수행하는 함수


 `void queryGetUsers(int index, int num, CMInfo cmInfo)`

 유저의 정보를 가져오는 함수. index는 유저테이블의 시작 인덱스 값을 넣는다. num은 가져올 유저의 수를 넣는다.


 `int queryUpdateUser(String name, String fieldName, String value, CMInfo cmInfo)`

 유저 테이블의 Column 값을 갱신하는 함수. fieldName에 유저 테이블의 칼럼 이름(column name)을 입력한다. value에 해당 칼럼의 값을 입력한다.(바꾸고 싶은 값으로 입력하는듯 하다.)


 `int queryDeleteUser(String name, CMInfo cmInfo)`

 유저 정보 삭제 함수.


 `void queryTruncateUserTable(CMInfo cmInfo)`

 유저 테이블을 한번에 Clear한다.

<br><br>



14.3.4 Friend table 관리

 유의 사항 - CMInfo 객체는 stub객체(CMServerStub cmStub = server.getServerStub() 로 생성)의 getCMInfo()함수를 통해 가져올 수 있다.


 `int queryInsertFriend(String strUserName, String strFriendname, CMInfo cmInfo)`

 친구를 추가하는 함수. 유저이름과 해당 유저에 추가할 친구이름을 넣는다.


 `int queryDeleteFriend(String strUserName, String strFriendName, CMInfo cmInfo)`

 친구 삭제 함수. 유저 이름과 삭제할 친구이름을 넣는다.


 `void queryGetFreindsList(String strUserName, CMInfo cmInfo)`

 해당 유저(strUserName에 해당하는 유저)의 친구들을 검색한다.


 `void queryGetRequestersList(String strUserName, CMInfo cmInfo)`

 해당 유저(strUserName)를 친구로 추가한 유저들을 검색한다.

<br><br>



14.3.5 SNS content table 관리

 유의사항 - CMInfo 객체는 stub객체(CMServerStub cmStub = server.getServerStub() 로 생성)의 getCMInfo()함수를 통해 가져올 수 있다.


 `int queryInsertSNSContent(String userName, String text, int nNumAttachedFiles, int nReplyOf, int nLevelOfDisclosure, CMInfo cmInfo)`

 SNS content를 추가하는 함수. text에는 텍스트 메세지, nNumAttachedFiles에는 첨부파일의 수, nReplyOf에는 replying ID값, nLevelOfDisclosure는 얼마나 공개할 것인지 값을 넣는다.


 `ResultSet queryGetSNSContent(String strRequester, String strWriter, int index, int num, CMInfo mInfo)`

 SNS content를 검색하는 함수. strRequester는 requester의 이름, strWriter는 content writer의 이름, index에는 요청된 content list의 시작 인덱스 값, num에는 가져올 메세지들의 수를 넣는다.


 `int queryUpdateSNSContent(int seqNum, String fieldName, String value, CMInfo cmInfo)`

 컨텐츠 테이블의 Column값을 갱신한다. seqNum은 컨텐츠의 시퀀스 넘버, fieldName은 컨텐츠 테이블의 해당 column 이름, value는 해당 칼럼에 갱신하려는 값을 넣는다.


 `int queryDeleteSNSContent(int seqNum, CMInfo cmInfo)`

 SNS content 삭제 함수. 지울 컨텐츠의 시퀀스 넘버를 넣는다.


 `void queryTruncateSNSContentTable(CMInfo cmInfo)`

 컨텐트 테이블 Clear 함수.

<br><br>




14.3.6 SNS content의 첨부파일 테이블 관리

 유의사항 - CMInfo 객체는 stub객체(CMServerStub cmStub = server.getServerStub() 로 생성)의 getCMInfo()함수를 통해 가져올 수 있다.



 `int queryInsertSNSAttachedFile(int nContentID, String strFilePath, String strFileName, CMInfo mInfo)`

 첨부 파일 추가 함수. nContentID는 해당 첨부파일과 관련된 content의 ID 값. strFilePath는 파일 디렉토리 경로를 넣어준다.


 `ResultSet queryGetSNSAttachedFile(int nContentID, CMInfo cmInfo)`

 첨부파일을 가져오는 함수. nContentID는 해당 첨부파일과 관련된 contentID 값.


 `int queryUpdateSNSAttachedFile(int seqNum, String fieldName, String value, CMInfo cmInfo)`

 첨부파일 테이블의 Column값 갱신 함수. seqNum은 해당 첨부파일 테이블의 시퀀스 넘버. fieldName은 갱신할 테이블의 column 이름. value에는 갱신할 값을 넣는다.


 `int queryDeleteSNSAttachedFile(int seqNum, CMInfo cmInfo)`

 첨부파일 삭제 함수. seqNum 첨부파일 테이블의 시퀀스 넘버를 넣는다.


 `void queryTruncateSNSAttachedFileTable(CMInfo cmInfo)`

 첨부파일 테이블 Clear 함수.

<br><br>


15. 다중 서버 관리


 CM을 사용하는 client-server 어플리케이션은 하나의 default 서버와 이 서버와 통신하는 클라이언트를 사용한다. 몇몇의 분산 어플리케이션은 분산 처리 기능을 제공하기위해 다중 서버를 필요로 한다. 이를 위해 CM은 개발자가 하나 이상의 CM 서버를 사용에 제한을 두지 않는다. 부가적으로 사용되는 서버는 디폴트 서버와 같은 기능들을 사용할 수 있다. 이때, 디폴트 서버가 다른 부가적인 서버들을 관리하게 되고 이를통해 클라이언트가 부가적 서버와 상호작용(예를 들어 커넥션)하는 것을 돕는다. 15장은 부가 서버의 개발, CM network에 참여방법 및 클라이언트와의 interaction에 대해 본다.

<br><br>
15.1 부가 서버 구성
