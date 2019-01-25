# Message Handler For MirrorMaker to use different topic names on target Kafka Cluster
This work is based on **Opencore** [example](https://github.com/opencore/mirrormaker_topic_rename).

Unlike Opencore handler, this one persists Kafka message headers.

More about [**MirrorMaker**](https://docs.confluent.io/current/multi-dc-replicator/mirrormaker.html)

Setup
=====
To use this code with MirrorMaker you can build it your self or use one which is committed in the repository.

To build the code run:
```
mvn package
```

Usage
=====
Before starting MirrorMaker you need to include the jar file in your classpath:

``` bash
export CLASSPATH=/home/path_to_jar_file/renametopichandler-1.0-SNAPSHOT.jar
```


When starting MirrorMaker you will need to add:

**--message.handler**:
This takes the class name of the handler class to use

**--message.handler.args**:
This is used to configure which topics to change, it should have the following format: source_topic_A,target_topicA;source_topic_B,target_topic_B;...

``` bash
kafka-mirror-maker --consumer.config consumer.cfg --producer.config producer.cfg --whitelist "source1|source2" --message.handler com.slavirok.RenameTopicHandler --message.handler.args "source_topic_A,target_topicA;source_topic_A,target_topic_A"
```

