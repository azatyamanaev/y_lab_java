CREATE USER liquibase WITH CREATEROLE PASSWORD '498d4840-8c47-460d';
CREATE DATABASE habits_app_db WITH OWNER liquibase;

\connect habits_app_db;

CREATE SCHEMA IF NOT EXISTS liquibase AUTHORIZATION liquibase;
CREATE SCHEMA IF NOT EXISTS entity AUTHORIZATION liquibase;

\q;