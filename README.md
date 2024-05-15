# Chess Game

[![Build Status](https://github.com/julienco03/Chess_SA/actions/workflows/scala.yml/badge.svg?branch=main)](https://github.com/julienco03/Chess_SA/actions/workflows/scala.yml)
[![Coverage Status](https://coveralls.io/repos/github/julienco03/Chess_SA/badge.svg?branch=main)](https://coveralls.io/github/julienco03/Chess_SA?branch=main)
![RepoSize](https://img.shields.io/github/repo-size/julienco03/Chess_SA)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MI)

## What is this project
A chess game developed in Scala 3 as an sbt project, running in multiple Docker container.


## How to install
1. Clone the repository:
    ```bash
    git clone https://github.com/julienco03/Chess_SA.git
    cd Chess_SA
    ```
2. Ensure Docker and Docker-Compose are installed on your system. Instructions can be found [here](https://docs.docker.com/get-docker/) and [here](https://docs.docker.com/compose/install/).

## How to start project with Docker Compose
1. Build and start the containers with Docker-Compose:
    ```bash
    docker compose up --build
    ```
2. The REST API service should now be accessible via [localhost:8000](http://localhost:8000). Check the status with:
    ```bash
    docker ps
    ```
3. To stop the containers, use:
    ```bash
    docker compose down
    ```

## How to use the MySQL database
If you want to look into the MySQL database or change values etc., do this:
1. ```mysql -h localhost -P 3306 -u chess_user -pchess_password```
3. Inside the MySQL shell, enter: ```USE chess_db;```
4. E.g. for reading all entries inside the Boards table, use: ```SELECT * FROM boards;```

NOTE: Values like host, port, user and password are specified in ```src/main/resources/application.conf``` and can be changed.

## Contributors
| [Julian Klimek](https://github.com/julienco03)  |  [TeefanDev](https://github.com/TeefanDev) |
|---|---|
| ![image](https://github-readme-streak-stats.herokuapp.com/?user=julienco03) | ![image](https://github-readme-streak-stats.herokuapp.com/?user=TeefanDev)  |
