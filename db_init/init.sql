-- primary/init.sql

-- Подключаемся к созданной базе данных
\c my_app_db

-- Создаем технического пользователя для репликации, который сможет читать WAL
CREATE USER replicator WITH REPLICATION ENCRYPTED PASSWORD 'replicator_password';

-- Дозаписываем правило доступа в pg_hba.conf внутри контейнера мастера
\! echo "host replication replicator all scram-sha-256" >> /var/lib/postgresql/18/docker/pg_hba.conf

-- Принудительно заставляем сервер перечитать конфигурацию сети
\! pg_ctl reload
