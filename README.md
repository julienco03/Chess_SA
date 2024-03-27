## Further development of our Chess game as software product for the lecture Software Architecture at HTWG Konstanz

![bannerImage](chess_banner.jpeg)

---
[![Build Status](https://github.com/PanterSoft/Chess/actions/workflows/scala.yml/badge.svg?branch=main)](https://github.com/PanterSoft/Chess/actions/workflows/scala.yml)
[![Coverage Status](https://coveralls.io/repos/github/PanterSoft/Chess/badge.svg?branch=main)](https://coveralls.io/github/PanterSoft/Chess?branch=main)
![RepoSize](https://img.shields.io/github/repo-size/PanterSoft/Chess)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MI)
![Forks](https://img.shields.io/github/forks/PanterSoft/Chess?color=green&style=social)
![Watcher](https://img.shields.io/github/watchers/PanterSoft/Chess?style=social)

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
