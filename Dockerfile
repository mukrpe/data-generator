FROM guenter/sbt_scala_java:latest

RUN apt-get update
RUN apt-get install -y software-properties-common
Run apt-get install -y python-pip
Run pip install ansible