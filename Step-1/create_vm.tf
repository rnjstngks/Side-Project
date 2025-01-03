resource "vsphere_virtual_machine" "master_1" {
  name             = "master-1"
  resource_pool_id = data.vsphere_compute_cluster.cluster.resource_pool_id
  datastore_id     = data.vsphere_datastore.datastore.id
  num_cpus         = 4
  memory           = 8192
  guest_id         = "ubuntu64Guest"
  network_interface {
    network_id = data.vsphere_network.network_99.id
  }
  disk {
    label = "disk"
    size  = 300
  }
  clone {
    template_uuid = data.vsphere_virtual_machine.template.id
    customize {
        linux_options {
            host_name = "master-1"
            domain = "smrc.klab-a"
        }
        network_interface {
            ipv4_address = "10.10.99.11"
            ipv4_netmask = 24
      }
      ipv4_gateway = "10.10.99.1"
      dns_server_list = [ "10.10.92.201" ]
    }
  }
  provisioner "remote-exec" {

    connection { # connection 해당 옵션이 inline 보다 앞에와야지 ssh 접속이 원활하게 됩니다.
      type     = "ssh"
      user     = "root"
      password = "1234"
      host     = "10.10.99.11"
    }
    
    inline = [
      "mkdir -p /root/.ssh",
      "echo 'ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIP57+TY2n0a0bcZqZ0I4VfD2lsKSHpcLNWW+bHWPlhYq han@S2300289' >> /root/.ssh/authorized_keys",
      "chmod 600 /root/.ssh/authorized_keys",
      "chmod 700 /root/.ssh"
    ]
  }
}

resource "vsphere_virtual_machine" "master_2" {
  name             = "master-2"
  resource_pool_id = data.vsphere_compute_cluster.cluster.resource_pool_id
  datastore_id     = data.vsphere_datastore.datastore.id
  num_cpus         = 4
  memory           = 8192
  guest_id         = "ubuntu64Guest"
  network_interface {
    network_id = data.vsphere_network.network_99.id
  }
  disk {
    label = "disk"
    size  = 300
  }
  clone {
    template_uuid = data.vsphere_virtual_machine.template.id
    customize {
        linux_options {
            host_name = "master-2"
            domain = "smrc.klab-a"
        }
        network_interface {
            ipv4_address = "10.10.99.12"
            ipv4_netmask = 24
      }
      ipv4_gateway = "10.10.99.1"
      dns_server_list = [ "10.10.92.201" ]
    }
  }
  provisioner "remote-exec" {

    connection { # connection 해당 옵션이 inline 보다 앞에와야지 ssh 접속이 원활하게 됩니다.
      type     = "ssh"
      user     = "root"
      password = "1234"
      host     = "10.10.99.12"
    }
    
    inline = [
      "mkdir -p /root/.ssh",
      "echo 'ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIP57+TY2n0a0bcZqZ0I4VfD2lsKSHpcLNWW+bHWPlhYq han@S2300289' >> /root/.ssh/authorized_keys",
      "chmod 600 /root/.ssh/authorized_keys",
      "chmod 700 /root/.ssh"
    ]
  }
}

resource "vsphere_virtual_machine" "master_3" {
  name             = "master-3"
  resource_pool_id = data.vsphere_compute_cluster.cluster.resource_pool_id
  datastore_id     = data.vsphere_datastore.datastore.id
  num_cpus         = 4
  memory           = 8192
  guest_id         = "ubuntu64Guest"
  network_interface {
    network_id = data.vsphere_network.network_99.id
  }
  disk {
    label = "disk"
    size  = 300
  }
  clone {
    template_uuid = data.vsphere_virtual_machine.template.id
    customize {
        linux_options {
            host_name = "master-3"
            domain = "smrc.klab-a"
        }
        network_interface {
            ipv4_address = "10.10.99.13"
            ipv4_netmask = 24
      }
      ipv4_gateway = "10.10.99.1"
      dns_server_list = [ "10.10.92.201" ]
    }
  }
  provisioner "remote-exec" {

    connection { # connection 해당 옵션이 inline 보다 앞에와야지 ssh 접속이 원활하게 됩니다.
      type     = "ssh"
      user     = "root"
      password = "1234"
      host     = "10.10.99.13"
    }
    
    inline = [
      "mkdir -p /root/.ssh",
      "echo 'ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIP57+TY2n0a0bcZqZ0I4VfD2lsKSHpcLNWW+bHWPlhYq han@S2300289' >> /root/.ssh/authorized_keys",
      "chmod 600 /root/.ssh/authorized_keys",
      "chmod 700 /root/.ssh"
    ]
  }
}

resource "vsphere_virtual_machine" "worker_1" {
  name             = "worker-1"
  resource_pool_id = data.vsphere_compute_cluster.cluster.resource_pool_id
  datastore_id     = data.vsphere_datastore.datastore.id
  num_cpus         = 4
  memory           = 8192
  guest_id         = "ubuntu64Guest"
  network_interface {
    network_id = data.vsphere_network.network_99.id
  }
  disk {
    label = "disk"
    size  = 300
  }
  clone {
    template_uuid = data.vsphere_virtual_machine.template.id
    customize {
        linux_options {
            host_name = "worker-1"
            domain = "smrc.klab-a"
        }
        network_interface {
            ipv4_address = "10.10.99.14"
            ipv4_netmask = 24
      }
      ipv4_gateway = "10.10.99.1"
      dns_server_list = [ "10.10.92.201" ]
    }
  }
  provisioner "remote-exec" {

    connection { # connection 해당 옵션이 inline 보다 앞에와야지 ssh 접속이 원활하게 됩니다.
      type     = "ssh"
      user     = "root"
      password = "1234"
      host     = "10.10.99.14"
    }
    
    inline = [
      "mkdir -p /root/.ssh",
      "echo 'ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIP57+TY2n0a0bcZqZ0I4VfD2lsKSHpcLNWW+bHWPlhYq han@S2300289' >> /root/.ssh/authorized_keys",
      "chmod 600 /root/.ssh/authorized_keys",
      "chmod 700 /root/.ssh"
    ]
  }
}

resource "vsphere_virtual_machine" "worker_2" {
  name             = "worker-2"
  resource_pool_id = data.vsphere_compute_cluster.cluster.resource_pool_id
  datastore_id     = data.vsphere_datastore.datastore.id
  num_cpus         = 4
  memory           = 8192
  guest_id         = "ubuntu64Guest"
  network_interface {
    network_id = data.vsphere_network.network_99.id
  }
  disk {
    label = "disk"
    size  = 300
  }
  clone {
    template_uuid = data.vsphere_virtual_machine.template.id
    customize {
        linux_options {
            host_name = "worker-2"
            domain = "smrc.klab-a"
        }
        network_interface {
            ipv4_address = "10.10.99.15"
            ipv4_netmask = 24
      }
      ipv4_gateway = "10.10.99.1"
      dns_server_list = [ "10.10.92.201" ]
    }
  }
  provisioner "remote-exec" {

    connection { # connection 해당 옵션이 inline 보다 앞에와야지 ssh 접속이 원활하게 됩니다.
      type     = "ssh"
      user     = "root"
      password = "1234"
      host     = "10.10.99.15"
    }
    
    inline = [
      "mkdir -p /root/.ssh",
      "echo 'ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIP57+TY2n0a0bcZqZ0I4VfD2lsKSHpcLNWW+bHWPlhYq han@S2300289' >> /root/.ssh/authorized_keys",
      "chmod 600 /root/.ssh/authorized_keys",
      "chmod 700 /root/.ssh"
    ]
  }
}

resource "vsphere_virtual_machine" "worker_3" {
  name             = "worker-3"
  resource_pool_id = data.vsphere_compute_cluster.cluster.resource_pool_id
  datastore_id     = data.vsphere_datastore.datastore.id
  num_cpus         = 4
  memory           = 8192
  guest_id         = "ubuntu64Guest"
  network_interface {
    network_id = data.vsphere_network.network_99.id
  }
  disk {
    label = "disk"
    size  = 300
  }
  clone {
    template_uuid = data.vsphere_virtual_machine.template.id
    customize {
        linux_options {
            host_name = "worker-3"
            domain = "smrc.klab-a"
        }
        network_interface {
            ipv4_address = "10.10.99.16"
            ipv4_netmask = 24
      }
      ipv4_gateway = "10.10.99.1"
      dns_server_list = [ "10.10.92.201" ]
    }
  }
  provisioner "remote-exec" {

    connection { # connection 해당 옵션이 inline 보다 앞에와야지 ssh 접속이 원활하게 됩니다.
      type     = "ssh"
      user     = "root"
      password = "1234"
      host     = "10.10.99.16"
    }
    
    inline = [
      "mkdir -p /root/.ssh",
      "echo 'ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIP57+TY2n0a0bcZqZ0I4VfD2lsKSHpcLNWW+bHWPlhYq han@S2300289' >> /root/.ssh/authorized_keys",
      "chmod 600 /root/.ssh/authorized_keys",
      "chmod 700 /root/.ssh"
    ]
  }
}