# monopoly

## Что умеет игра
1. Создает игру на четырех человек
2. Каждый ход бросает два кубика и двигает персонажа
3. Персонажу предоставляется возможность купить клетку, если не занята
4. Клетки кликабельны, там менюшка всплывает какая-то.
5. В случае покупки всех клеток с улицы при нажатии на клетку с этой улицы есть возможность ее улучшить(улицы подчеркнуты одинаковой обводкой, по умолчанию две клетки, если что карта вроде бы умеет скейлиться, но такую функцию не добавлял)
6. Если во время хода выпал дубль (одинаковые кубики) ход делается еще раз. (так продолжается три раза, потом игрока в тюрьму отправляет)
7. Случайные клетки могут выполнить одно из четырех действий - дать карточку на бесплатный выход из тюрьмы, дать денег, забрать денег или отправить в тюрьму
8. Free Parking делает пропуск хода
9. Go to jail тоже отправляет в тюрьму
10. В тюрьме игрок может потратить пропуск при наличии, заплатить 50 денег при наличии, либо кидать кубики каждый ход пока не выпадет дубль. Тогда он сможет выйти.
11. Проходя полный круг игроку начисляется 50 денег (если не ошибаюсь, если что все основные константы вынесены в Config)
12. Игра заканчивается когда у троих игроков деньги перевалят за <= 0. Тогда должно вывести победителя и запустить новую игру. Из игры можно выйти нажав Esc.

## Структура проекта
![Monopoly structure](https://github.com/imbeer/monopoly/assets/76579340/e6b22b65-f4ca-410b-b0ab-fd63999a85af)
(вроде бы избежал циклических ссылок)

Теперь поясняю. Постарался повторить MVC - в Main Window находятся все обработчики событий которые дергают методы из Game. В качестве view - GameView - JPanel на котором все отрисовывается.

GameWorld - по сути хранилище всех игроков, всех клеток и прочая, умеет выдавать следующего игрока и сам себя заполнять. Цикл игроков реализован с помощью списка, из которого постепенно выкидываются банкроты. 
Сделал так для того, чтобы было удобно высчитывать когда игру пора заканчивать + чтобы в "nextPlayer" не проходилось создавать цикл с проверкой или рекурсии вызывать.

Теперь клетки - Tile. Так как нам не придется менять размер количества клеток во время игры, они хранятся в обычном массиве. 
Сначала думал создать интерфейс для клеток, но решил что это будет лишнее и базовый Tile будет обладать функционалом обычной клетки, которую можно купить. Она знает о своем владельце и об улице (Street) на которой находится.
Остальные клетки от него наследуются, но так как большинство правил обрабатывает Game, они скорее как болванки, кроме GoToJail и ChanceTile. Но в случае чего им можно докрутить улучшенную отрисовку или еще чего, так что есть потенциал для расширения.

Player - базовый класс игрока. Знает свои координаты на поле. Основная механика игры - ask - у игрока спрашивается, хочет ли он выполнить действие - он отвечает да или нет. У Bot этот метод переопределен - там выдает случайный ответ, что делает игру с ботами интересной и непредсказуемой (нет).

Street - знает владельцев всех клеток на улице. Когда игра обращается к клетке и спрашивает, можно ли ее улучшить, клетка обращается к улице и узнает одинаковые ли владельцы на всех клетках, если да, то ответ положительный.
