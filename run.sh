#!/bin/sh

cd webapp
npm start &
cd ..
java -jar sparkapi-0.0.1-SNAPSHOT.jar &

