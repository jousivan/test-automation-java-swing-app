package br.com.ajss.automation.testutil;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class TAR {

    private TAR() {

    }

    public static void compress(String name, File... files) throws IOException {
        try (TarArchiveOutputStream out = getTarArchiveOutputStream(name)){
            for (File file : files){
                addToArchiveCompression(out, file, ".");
            }
        }
    }

    public static void decompress(String in, File out) throws IOException {
        try (TarArchiveInputStream fin = new TarArchiveInputStream(new GzipCompressorInputStream(new FileInputStream(in)))){
            TarArchiveEntry entry;
            while ((entry = fin.getNextTarEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }
                File curfile = new File(out, entry.getName());
                File parent = curfile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                IOUtils.copy(fin, new FileOutputStream(curfile));
            }
        }
    }
    
    public static void decompressdeb(String in, File out) throws IOException, ArchiveException {
        //LOG.info(String.format("Unzipping deb file %s.", deb.getAbsoluteFile()));
        //LOG.info(String.format("Into dir %s.", outDir.getAbsoluteFile()));

        final List<File> unpackedFiles = new LinkedList<File>();
        final InputStream is = new FileInputStream(in); 
        final ArArchiveInputStream debInputStream = (ArArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("ar", is);
        ArArchiveEntry entry = null; 
        while ((entry = (ArArchiveEntry)debInputStream.getNextEntry()) != null) {
            //LOG.info("Read entry");
            final File outputFile = new File(out, entry.getName());
            final OutputStream outputFileStream = new FileOutputStream(outputFile); 
            IOUtils.copy(debInputStream, outputFileStream);
            outputFileStream.close();
            unpackedFiles.add(outputFile);
        }
        debInputStream.close(); 
        //return unpackedFiles;
    	
    	
    }

    private static TarArchiveOutputStream getTarArchiveOutputStream(String name) throws IOException {
        TarArchiveOutputStream taos = new TarArchiveOutputStream(new GzipCompressorOutputStream(new FileOutputStream(name)));
        // TAR has an 8 gig file limit by default, this gets around that
        taos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR);
        // TAR originally didn't support long file names, so enable the support for it
        taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
        taos.setAddPaxHeadersForNonAsciiNames(true);
        return taos;
    }

    private static void addToArchiveCompression(TarArchiveOutputStream out, File file, String dir) throws IOException {
        String entry = dir + File.separator + file.getName();
        if (file.isFile()){
            out.putArchiveEntry(new TarArchiveEntry(file, entry));
            try (FileInputStream in = new FileInputStream(file)){
                IOUtils.copy(in, out);
            }
            out.closeArchiveEntry();
        } else if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null){
                for (File child : children){
                    addToArchiveCompression(out, child, entry);
                }
            }
        } else {
            System.out.println(file.getName() + " is not supported");
        }
    }
}
