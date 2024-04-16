## Further development of our Chess game as software product for the lecture Software Architecture at HTWG Konstanz

![bannerImage](chess_banner.jpeg)

---
[![Build Status](https://github.com/julienco03/Chess_SA/actions/workflows/scala.yml/badge.svg?branch=main)](https://github.com/julienco03/Chess_SA/actions/workflows/scala.yml)
[![Coverage Status](https://coveralls.io/repos/github/julienco03/Chess_SA/badge.svg?branch=main)](https://coveralls.io/github/julienco03/Chess_SA?branch=main)
![RepoSize](https://img.shields.io/github/repo-size/julienco03/Chess_SA)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MI)
![Forks](https://img.shields.io/github/forks/julienco03/Chess_SA?color=green&style=social)
![Watcher](https://img.shields.io/github/watchers/julienco03/Chess_SA?style=social)

---

## How to run Docker container with X11

### Building the Container
```docker build -t chess:v1 .```

### Running the Container
```
xhost +

ip=$(ifconfig en0 | grep inet | awk '$1=="inet" {print $2}')

docker run -e DISPLAY=$ip:0 -v /tmp/.X11-unix:/tmp/.X11-unix -ti chess:v1

xhost -
 ```

---

## Contributors
| [Julian Klimek](https://github.com/julienco03)  |  [TeefanDev](https://github.com/TeefanDev) |
|---|---|
| ![image](https://github-readme-streak-stats.herokuapp.com/?user=julienco03) | ![image](https://github-readme-streak-stats.herokuapp.com/?user=TeefanDev)  |
