#### Используйте команды операционной системы Linux и создайте две новых директории – «Игрушки для школьников» и «Игрушки для дошколят» ####
mkdir toys_schoolers toys_preschoolers

#### Создайте в директории «Игрушки для школьников» текстовые файлы - «Роботы», «Конструктор», «Настольные игры» ####
touch toys_schoolers/robots toys_schoolers/legos toys_schoolers/tabletops


#### Создайте в директории «Игрушки для дошколят» текстовые файлы «Мягкие игрушки», «Куклы», «Машинки» ####
touch toys_preschoolers/dolls toys_preschoolers/cars toys_preschoolers/stuffies


#### Объединить 2 директории в одну «Имя Игрушки» ####
mkdir toys_name;
mv toys_preschoolers/dolls toys_preschoolers/cars toys_preschoolers/stuffies toys_schoolers/robots toys_schoolers/legos toys_schoolers/tabletops toys


#### Переименовать директорию «Имя Игрушки» в «Игрушки» ####
mv toys_name toys


#### Просмотреть содержимое каталога «Игрушки». ####
ls -al toys


#### Установить и удалить snap-пакет (Любой). ####
snap find 'player'
sudo snap install pogo
sudo snap remove pogo

#### Добавить произвольную задачу для выполнения каждые 3 минуты (Например, запись в текстовый файл чего-то или копирование из каталога А в каталог Б). ####
crontab -e
*/3 * * * * date >> /home/gblin/mid-attestation/crontask
