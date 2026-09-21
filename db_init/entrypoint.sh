#!/bin/bash

#Немедленно останавливаем скрипт, если любая из команд завершается с ошибкой
set -e

#Ждем, пока основная база данных станет доступной для подключения
echo "Ожидание готовности primary"
until pg_isready -h primary -p 5432;
do
sleep 1
done
echo "Primary готова к подключению"

#Передаем пароль для pg_basebackup
export PGPASSWORD='replicator_password'

#Проверяем необходимость копирования данных из primary
if [ ! -s "$PGDATA/PG_VERSION" ]; then
echo "Директория данных пуста. Начинаем инициализацию реплики из primary"

#Создаем полную физическую копию primary
#Флаг -R автоматически создает standby.signal (запуск базы как реплики)
pg_basebackup -h primary -D $PGDATA -U replicator -v -P --wal-method=stream -R
echo "Бэкап успешно скопирован"

else
echo "Найдены существующие данные. Пропускаем этап инициализации реплики из primary"
fi

#Передаем управление официальному скрипту запуска СУБД
exec docker-entrypoint.sh postgres
