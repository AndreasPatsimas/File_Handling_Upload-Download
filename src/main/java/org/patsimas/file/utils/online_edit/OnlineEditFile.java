package org.patsimas.file.utils.online_edit;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import java.io.IOException;
import java.util.List;

public class OnlineEditFile {

    public static void main(String [] args) throws IOException {

        Sardine sardine = SardineFactory.begin("devsamii", "Apollo11!");

        List<DavResource> davResources = sardine.list("https://solcloud.solcrowe.gr/cloud/remote.php/webdav/999999/2051");

        davResources.forEach(davResource -> System.out.println(davResource.getName()));
    }
}
