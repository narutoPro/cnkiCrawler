package top.chende.shuinikg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.chende.shuinikg.model.Term;
import top.chende.shuinikg.repository.TermRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: chende
 * @date: 2019/7/22 22:59
 * @description:
 */
@Service
public class TermService {
    @Autowired
    TermRepository termRepository;
    String PATH="/Users/chende/Desktop/term2.txt";


    public  List allTerm(){
        return termRepository.findAll();
    }
    public  List getTermFile(String path){
        List<Term>  terms=new ArrayList<>();

        try
        {
            File file =new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader bf=new BufferedReader(fr);
            Scanner scanner=new Scanner(file);
            String s1;
            String s2;

            while (bf.ready()){
                Term term=new Term();
                s1=bf.readLine();
                String[] ss=s1.split("[ qwertyuiopasdfghjklzxcvbnm;\t]+");
                String s11=ss[0];
                String s22=s1.split(s11)[1];
                s2=bf.readLine();
                term.setTName(s11);
                term.setTEn(s22);
                term.setTDescription(s2);
                terms.add(term);
            }
        }
        catch (FileNotFoundException fnt){

        }catch (IOException io){
        }
        try {
            termRepository.saveAll(terms);
            termRepository.flush();
        }catch (Exception ex){
            ex.printStackTrace();
            System.err.println("file term insert error!!!");
        }

        return terms;
    }
    /**
     *
     * @param term
     * @return
     */
    public Term addTerm(Term term){
        return termRepository.saveAndFlush(term);

    }
    public Term termDetail(Long id){return  termRepository.findById(id).get();}
    public void delTerm(Long id){
        termRepository.deleteById(id);
    }
}
