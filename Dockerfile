FROM guenter/sbt_scala_java:latest

RUN apt-get update && \
    apt-get install -y \
        software-properties-common \
        python-pip  \
        vim && \
    pip install ansible
RUN mkdir -p /dataIngest/sketchbench-data-ingestion
COPY . /dataIngest/sketchbench-data-ingestion/
WORKDIR /dataIngest/sketchbench-data-ingestion
RUN sbt assembly