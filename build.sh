#! /usr/bin/bash
git pull && mvn package && sudo docker build -t digitalinventory:1.1.0 .