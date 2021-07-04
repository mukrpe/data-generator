FROM guenter/sbt_scala_java:latest

RUN apt-get update && apt-get install -y software-properties-common && apt-get install -y python-pip && pip install ansible && apt-get install -y git
RUN mkdir /dataIngest
RUN mkdir /dataIngest/sketchbench-data-ingesiton
RUN cd /dataIngest/sketchbench-data-ingesiton \
           git clone https://github.com/mukrpe/sketchbench-data-ingestion.git
WORKDIR /dataIngest/sketchbench-data-ingesiton
RUN sbt assembly

# ansible-playbook -vvvv tools/configuration/plays/benchmark-runner-beam.yml

