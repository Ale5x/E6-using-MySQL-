 Использована база данных MySQL. Данные пользователей и каталог книг хранится в MySQL.
    Пароль в открытом виде не хранится, использован алгоритм шифрования.
    При добавлении книги в книжный фонд, происходит отправка сообщений всем пользователям, зарегистрируемых в библиотеке

    Пользователь может совершать следующие действия:
        • Просматривать каталог. Реализован постраничный просмотр, на страние 20 книг.
        • Совершать поиск книг по автору.
        • Совершать поиск книг по произвеению.
        • Предложить книгу для добавления в библиотеку. Сообщение пользователя отправляется на email администратору
     а также сохраняется в текстовый файл в корень проекта Library/Message for Admin.
        • Сохранение каталога на жесткий диск (Доступно сохранение по умолчанию D:\, а также на место указанным
    пользователем)

    Администратор может совершать следующие действия:
        • Добавлять книги в каталог. После добавления книги, система разошлёт всем зарегистрируемым пользователям
    сообщение
    о появлении новой книги в библиотеке.
        • Просматривать все данные всех зарегистрируемых пользователей, а также сохранение этих данных в файл txt
     (Доступно сохранение по умолчанию в корень проекта Library\Library users data, а также на место указанным 
    администратором).
        • Добавлять пользователя в администраторы.
        • Переводить пользователя из уровня администраторов в обычные пользователи.
