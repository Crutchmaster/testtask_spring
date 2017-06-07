# testtask_spring

ТЗ выполнено с использованием spring.boot, jpa и codehaus jackson. Для работы нужна java 8, postgresql 9.3, maven для сборки. Под linux для сборки и запуска есть соответствующий скрипт с говорящим названием. Под винду делать mvn package, запускать получившуюся (или нет) jar'ку из target и открывать браузером localhost:8080.
Для заполение бд перейти на http://localhost:8080/init База заполнится из json, который уже упакован в jar. На странице есть ссылки. Можно потыкать. Всё на агнлийском, но так надо, потом можно (но не нужно) будет сделать локализацию с каким-нибудь gettext'ом.
Структура.
Каждый товар имеет название, цену и количество. Это все прописывается юзером без контроля.Также, есть бренд и тип, выбираемые по списку. Еще есть набор атрибутов с набором фиксированных значений (деревянный/металлический/пластиковый/сплав фольга+картон) или без таковых, но с типом целое/вещественное (по идее должен быть контроль ввода, можно наделать еще кучу типов, но это никак не реализованно) и размерностью этих значений (мм/метр/км/кг/фунт/д.енот/итд). Входные данные обрабатываются.
API
Через 
http://localhost:8080/api/data/<тип>
Можно получить json данных из базы. Тип может быть следующий:
"types","attrs","attrValues","brands","items","itemTypes"
Соответсвенно на выходе получаем список типов, аттрибутов, возможных значений аттрибутов, брендов, товаров (со всеми кишками), типов товаров.
Редактировать можно через /api/edititem, post запросом, где нужно следующее:
id, brandId, cost, count, itemAttrId, ... ,attrValueStr, ... ,AttrValue, ... (ид товара, ид бренда, цена (число с точкой), количество товара (целое), массив ид аттрибутов товара, массив значений для этих аттрибутов и ид значений/размерностей). Всё экранируется и даже имеет не нужную защиту от вандалов. При ошибке вываливается json с одним полем error и лаконичным сообщением.
Хронометраж
~20 часов из которых
14 часов : вдумчивое чтение малополезных мануалов и SO.
5 часов : кодинг, костылестроение, велосипедостроение и танцы на граблях.
1 час : написание сей записки
