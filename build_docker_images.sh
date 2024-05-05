#!/bin/bash

MODULES=("controller" "logic" "persistence" "rest" "utils") # ui

build_dockerfile() {
    local module=$1
    echo "Building Dockerfile in $module..."
    docker build -t "$module"-image:latest $module
}

for module in "${MODULES[@]}"; do
    build_dockerfile "$module"
done
