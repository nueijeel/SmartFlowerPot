### 1. 객체란 무엇일까요?   
      
객체란 속성(property)과 동작(method)을 갖는 단위로 객체지향 프로그래밍에서는 클래스를 실체화해 객체를 생성한다.
객체는 캡슐화, 상속, 다형성 등의 특징을 가진다.   

### 2. Kotlin에서 널 가능성을 어떻게 컨트롤 할까요?   

kotlin에서는 null 허용 여부를 명시적으로 지정할 수 있다.
```kotlin
var a: String = "A"
var b: String? = null
```
자료형 뒤에 ?(nullable)을 붙여 null 허용 변수를 선언할 수 있다.   

* **조건문을 통한 null 검사**
```kotlin
// null 허용 변수 선언
val str: String? = null

if (str != null) {
	print("${str.length}")
}
```
str이 null이 아닐 경우 스마트 캐스트에 의해 str을 자동으로 non-nullable 타입으로 인식한다.   

* **? 연산자 (세이프 콜)**
```kotlin
var strLen: Int? = str?.length
```
str이 null이므로 메서드를 호출하지 않고 null을 반환하기 때문에 위 과정에서 NPE 발생하지 않음   

* **?: 연산자 (엘비스 연산자)**
```kotlin
var str2: String = str ?: "wanted"
```
str이 null이므로 지정한 디폴트 값이 반환되기 때문에 위 과정에서 NPE 발생하지 않음   

* **!! 연산자**
```kotlin
var strLen: Int = str!!.length
```
str이 null이지만 연산자에 의해 무시되고 메서드가 호출되어 위 과정에서 NPE가 발생한다.   


### 3. 코틀린의 interface 와 자바의 Interface차이

Interface : 추상 메서드만을 갖는 요소로, 하나의 클래스에 다수의 interface를 구현함으로써 다형성을 구현할 수 있게 한다.

코틀린과 자바의 interface 차이점
* **default 메서드**
	- Java : Java 8 부터만 default 키워드를 사용해 기본 구현이 있는 메서드를 정의할 수 있다.
	- Kotlin : 기본적으로 추상 메서드 외에도 구현이 있는 메서드를 정의할 수 있다.   
* **프로퍼티**
	- Java : 선언이 불가능하다.
	- Kotlin : 선언이 가능하다.   
* **생성자**
	- Java : 생성자를 가질 수 없다.
	- Kotlin : 생성자를 가질 수 있다.   


### 4. Sealed class가 무엇일까요?

Sealed Class는 자신을 상속받는 하위 클래스들을 관리하기 위해 사용하는 클래스이다. Sealed Class를 상속받는 각 서브 클래스에 대해 여러 개의 인스턴스를 생성할 수 있다. when 분기문이나 is 키워드를 사용해 인스턴스의 서브 클래스 타입에 맞는 동작을 수행하도록 명시할 수 있다.

### 5. Java와 Kotlin으로 각각 싱글톤 패턴을 구현해주세요

Singleton Pattern은 하나의 클래스에 오직 하나의 인스턴스 만을 갖도록 하는 디자인 패턴이다.
하나의 인스턴스를 다른 모듈들이 공유하며 사용하기 때문에 인스턴스화 비용이 줄어들고 재사용성을 높이지만, 독립적인 인스턴스를 만들기 어려워 의존성을 높인다.

**Java**
```Java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Kotlin**
```Kotlin
object Singleton { }
```
