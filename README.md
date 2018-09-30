# elk-tutorial

## About

This repository is part of a tutorial about [Elasticsearch, Logstash and Kibana](https://www.adictosaltrabajo.com/tutoriales/monitorizacion-de-logs-con-elasticsearch-logstash-y-kibana/).
It contains all the required configuration files and a Spring Boot example app to deploy and test the platform.

The config files allow for an easy setup on two VirtualBox machines with Vagrant and Ansible.

The app's only purpose is to generate log events which are read by Logstash and then analyzed and forwarded to Elasticsearch so that they can be later displayed on Kibana.

## License

All source code is released under the MIT license.

Feel free to use the Ansible roles for your own deployments; they are quite flexible.
