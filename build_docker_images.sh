#!/bin/bash

MODULES=("controller" "logic" "persistence" "rest" "ui" "utils")

for module in "${MODULES[@]}"; do
    docker build -t "$module"-image:latest $module
done
docker build -t src-image:latest .
