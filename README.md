# Health Checker Service

> Bitirme projesi için yazılmıştır :shipit:
## Amaç 

Bir arada çalışan servislerin ve bazı web sitelerinin sağlığının kontrol edilip gerekli durumlarda kullanıcılara alarm verilmesi.

## Health Checker Projesi

Proje kapsamında backend servislerin ve web sitelerinin sağlıklı olup olmadığının kontrolu yapılarak olumsuz durumlarda belirlenen kanallardan(Email, Telegram, Slack vs) alarm verilmesi.

- Servis saglikli mi kontrolu (servis saglikli degilse ilgili kanallardan alarm mesaji verilecek)
- Servisten donen response dogru mu kontrolu (contract test olarak da kullanilabilir)
- Projenin response time kontrolu/alarmi
  
## Projenin Kapsamı

Bir projenin gelistirilme asamasindan son kullaniciya cikis noktasina kadar olan tum sureci icerisine alan kapsamda urun gelistirilmesi.

  ###  Backend

  - Health check yapilacak servislerin eklenmesini ve duzenlenmesini saglayacak endpointler
  - Alarm kanallarının tanimlanip duzenlenmesini saglayacak endpointler
  - Kayitli servislerin sagligini kontrol edecek ve gerekiyorsa alarm acacak cron job
    
  ### Fronted

  - Health check yapilacak servislerin ve kurallarinin tanimlandigi bir kullanıcı arayüzü
  - Alarm kanallarının ayarlandigi bir arayuz (Email tanımlama,Telegram botu vs)
  - Kayit servislerin anlik durumunun gosterildigi bir ekran

## Deployment ve Test Surecleri

- Projede GitHub Workflows kullanilacak
- Projeye commit geldiginde GitHub Workflow uzerinde asagidaki surecler gerceklestirilecek:
    - Unit testler calisacak
    - Backend projemiz Docker imajina alinip build edilecek
    - Projenin belirlenen (Heroku, AWS, GCP vb.) ortamlara platforma deploy edilmesi
      
## Projenin Aşamaları

1. Kullanılacak teknolojilere karar verilmesi.
2. Proje için backend ve frontend icin dil seçimi belirlenmeli. (Java, go, vuejs, react etc.)
3. Proje ismi belirlenmeli.
4. Belirlenen isimle gitlab’de proje oluşturulur.
5. Proje backend ve frontend olarak gelistirilir
6. Localde testler tamamlanir
7. CI/CD adımlarının eklenir. (unit testler → build image → deploy to prod)
8. Deploy edilecek platform ile ilgili entegrasyon sureci yapilir
9. GitHub’taki pipeline’a deploy adiminin eklenmesi (deploy to prod)

<div style="background-color: #f7f7f7; padding: 10px; border-radius: 5px;">
  <h3 style="color: #333;">Kullanılan Teknolojiler :fire:</h3>
  <ul>
    <li><span style="background-color: #ff5733; color: white; padding: 3px 6px; border-radius: 3px;">Spring Boot</span></li>
    <li><span style="background-color: #007bff; color: white; padding: 3px 6px; border-radius: 3px;">Java</span></li>
    <li><span style="background-color: #7952b3; color: white; padding: 3px 6px; border-radius: 3px;">Postgresql</span></li>
    <li><span style="background-color: #7952b3; color: white; padding: 3px 6px; border-radius: 3px;">Docker</span></li>
    <li><span style="background-color: #7952b3; color: white; padding: 3px 6px; border-radius: 3px;">Data Rest</span></li>
    <li><span style="background-color: #7952b3; color: white; padding: 3px 6px; border-radius: 3px;">GCP</span></li>
    <li><span style="background-color: #7952b3; color: white; padding: 3px 6px; border-radius: 3px;">Github Pipelines</span></li>
  </ul>
</div>


```java
@SpringBootApplication
public class HealthChecker {

    public static void main(String[] args) {
        SpringApplication.run(HealthChecker.class, args);
    }
}
```
```java
mvn run
```


| ID | NAME | LAST STATUS | STATUS CODE |
| -------- | -------- | -------- |  -------- |
| 1 | ToDo List Get By ID | SUCCESS |  200 |
| 2 | ToDo List Get All | FAILED | 500 |



![Screenshot of a comment on a GitHub issue showing an image, added in the Markdown, of an Octocat smiling and raising a tentacle.](https://spring.io/img/extra/quickstart-2.png)

> [!WARNING]
> Spring projelerinde sıkça kullanılan Loombok kütüphanesi Mockito gibi bazı test kütüphaneleri ile birlikte build alındığında hata verebilmektedir. Bu sorundan kaçınmak için Loombok sürümünüzü değiştirebilirsiniz.

> [!NOTE]
> Spring Boot 3.x ve daha yukarı sürümlerde doğrudan swagger modülünü kullanamıyorsunuz. Bunu çözmek için 3.parti tool'lar eklenemeniz gerekiyor.

### Proje kodlanırken yararlanılan bazı kaynaklar aşağıda belirtilmiştir.
Hızlıca gerekli bağımlıkları ve paketleri oluşturmak için [Spring İnitializr](https://start.spring.io/)

Kolay okunabilen bir spring boot dökümantasyonu [Baeldung](https://www.baeldung.com/)
