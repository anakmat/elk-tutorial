# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure(2) do |config|
  # The most common configuration options are documented and commented below.
  # For a complete reference, please see the online documentation at
  # https://docs.vagrantup.com.

  # Every Vagrant development environment requires a box. You can search for
  # boxes at https://atlas.hashicorp.com/search.
  config.vm.box = "ubuntu/trusty64"

  # Disable automatic box update checking. If you disable this, then
  # boxes will only be checked for updates when the user runs
  # `vagrant box outdated`. This is not recommended.
  # config.vm.box_check_update = false

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine. In the example below,
  # accessing "localhost:8080" will access port 80 on the guest machine.
  # config.vm.network "forwarded_port", guest: 80, host: 8080

  # Create a private network, which allows host-only access to the machine
  # using a specific IP.
  # config.vm.network "private_network", ip: "192.168.33.10"

  # Create a public network, which generally matched to bridged network.
  # Bridged networks make the machine appear as another physical device on
  # your network.
  # config.vm.network "public_network"

  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  # config.vm.synced_folder "../data", "/vagrant_data"

  # Provider-specific configuration so you can fine-tune various
  # backing providers for Vagrant. These expose provider-specific options.
  # Example for VirtualBox:
  #
  # config.vm.provider "virtualbox" do |vb|
  #   # Display the VirtualBox GUI when booting the machine
  #   vb.gui = true
  #
  #   # Customize the amount of memory on the VM:
  #   vb.memory = "1024"
  # end
  #
  # View the documentation for the provider you are using for more
  # information on available options.

  # Define a Vagrant Push strategy for pushing to Atlas. Other push strategies
  # such as FTP and Heroku are also available. See the documentation at
  # https://docs.vagrantup.com/v2/push/atlas.html for more information.
  # config.push.define "atlas" do |push|
  #   push.app = "YOUR_ATLAS_USERNAME/YOUR_APPLICATION_NAME"
  # end

  # Enable provisioning with a shell script. Additional provisioners such as
  # Puppet, Chef, Ansible, Salt, and Docker are also available. Please see the
  # documentation for more information about their specific syntax and use.
  # config.vm.provision "shell", inline: <<-SHELL
  #   sudo apt-get update
  #   sudo apt-get install -y apache2
  # SHELL
  servers = ["appsserver", "statsserver"]
  memories = [2048, 8192]
  cpus = [2, 2]
  ssh_ports = [2223, 2224]
  kibana_ports = [0, 5601]
  es_http_ports = [0, 9200]
  es_transport_ports = [0, 9300]

  (0..servers.length - 1).each do |i|
    servername = servers[i]
    memory = memories[i]
    cpu = cpus[i]
    ssh_port = ssh_ports[i]
    kibana_port = kibana_ports[i]
    es_http_port = es_http_ports[i]
    es_transport_port = es_transport_ports[i]

    config.vm.provider "virtualbox" do |vb|
        vb.memory = memory
        vb.cpus = cpu
    end

    config.vm.define servername do |server|
      server.vm.hostname = servername
      server.vm.network "forwarded_port", guest: 22, host: ssh_port, id: "ssh"
      if kibana_port != 0
        server.vm.network "forwarded_port", guest: 5601, host: kibana_port
      end
      if es_http_port != 0
        server.vm.network "forwarded_port", guest: 9200, host: es_http_port
      end
      if es_transport_port != 0
        server.vm.network "forwarded_port", guest: 9300, host: es_transport_port
      end
      server.vm.provision "ansible" do |ansible|
        ansible.extra_vars = {
          ansible_host: "127.0.0.1",
          ansible_port: ssh_port,
          es_http_port: es_http_port,
          es_transport_port: es_transport_port,
          es_unicast_nodes: es_transport_ports.map{ |p| "10.0.2.2:#{p}" if p != 0 }.compact
        }
        ansible.inventory_path = "ansible/environments/tutorial/inventory"
        ansible.verbose = "vvv"
        ansible.playbook = "ansible/stats.yml"
      end
    end
  end
end
