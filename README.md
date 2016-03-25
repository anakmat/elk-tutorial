# elk-tutorial
Configuration files and Spring Boot example app to test Elasticsearch, Logstash and Kibana (ELK).

These config files allow for easy setup of an ELK deployment with Vagrant and Ansible.

The app's only purpose is to generate log events which are then read by Logstash, analyzed and forwarded to Elasticsearch so that they can be later displayed on Kibana.

Feel free to use the Ansible roles for your own deployments; they are quite flexible.
