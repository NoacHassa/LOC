import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;


public class countLOCfileTest {

  LOC counter;
  File f;
  
  @Before public void setUp() {
   
    counter=new LOC();
    
  }
    
  
  @Test
  public void noComments() {
    
    
     f= new File("keinKommentar.txt");
     counter.countLOCfile(f);
     assertEquals(counter.get_fileCounter(),12);
   
   
    
  }
  @Test
  public void singleLineComment() {
    
    f=new File("singleLineComment1.txt");
    counter.countLOCfile(f);
    assertEquals(counter.get_fileCounter(),0);
    f=new File("singleLineComment2.txt");
    counter.countLOCfile(f);
    assertEquals(counter.get_fileCounter(),5);
    
  }
  @Test
  public void multiLineComment() {
    
    f=new File("multiLineComment1.txt");
    counter.countLOCfile(f);
    assertEquals(counter.get_fileCounter(),0);
    
    f=new File("multiLineComment2.txt");
    counter.countLOCfile(f);
    assertEquals(counter.get_fileCounter(),2);
    
    f=new File("multiLineComment3.txt");
    counter.countLOCfile(f);
    assertEquals(counter.get_fileCounter(),3);
    
    f=new File("multiLineComment4.txt");
    counter.countLOCfile(f);
    assertEquals(counter.get_fileCounter(),3);
    
    
  }
  
  @Test
  public void mixedComment() {
    
    f=new File("mixedComment1.txt");
    counter.countLOCfile(f);
    System.out.println(counter.get_fileCounter());
    assertEquals(counter.get_fileCounter(),4);
    
    f=new File("mixedComment2.txt");
    counter.countLOCfile(f);
    System.out.println(counter.get_fileCounter());
    assertEquals(counter.get_fileCounter(),8);
   
    
  }
  
  @Test
  public void beginCommentTest() {
    
    String noComment="Just an easy example";
    assertFalse(counter.beginComment(noComment));
    
    String noComment1="/Just an easy example*/";
    assertFalse(counter.beginComment(noComment1));
    
    String singleLineComment1="//";
    assertFalse(counter.beginComment(singleLineComment1));
    
    String singleLineComment2="code //";
    assertFalse(counter.beginComment(singleLineComment2));
    
    String singleLineComment3="code // comment  /*";
    assertFalse(counter.beginComment(singleLineComment3));
    
    String multipleLineComment1="/*";
    assertTrue(counter.beginComment(multipleLineComment1));
    
    String multipleLineComment2="a/* comment*/";
    assertFalse(counter.beginComment(multipleLineComment2));
    
  }
  
  @Test
  public void onlyCommentTest() {
    
    counter.set_fileCounter(0);
    counter.set_commentOverRows(true);
    String multipleLineCommentEnds="*/";
    counter.onlyComment(multipleLineCommentEnds);
    assertEquals(counter.get_fileCounter(),0);
    assertFalse(counter.get_commentOverRows());
    
    counter.set_fileCounter(0);
    counter.set_commentOverRows(true);
    String multipleLineCommentEnds2="comment*/";
    counter.onlyComment(multipleLineCommentEnds2);
    assertEquals(counter.get_fileCounter(),0);
    assertFalse(counter.get_commentOverRows());
    
    counter.set_fileCounter(0);
    counter.set_commentOverRows(true);
    String multipleLineCommentEnds3="comment*/code";
    counter.onlyComment(multipleLineCommentEnds3);
    assertEquals(counter.get_fileCounter(),1);
    assertFalse(counter.get_commentOverRows());
    
    counter.set_fileCounter(0);
    counter.set_commentOverRows(true);
    String multipleLineCommentStarts1="*//*";
    counter.onlyComment(multipleLineCommentStarts1);
    assertEquals(counter.get_fileCounter(),0);
    assertTrue(counter.get_commentOverRows());
    
    counter.set_fileCounter(0);
    counter.set_commentOverRows(true);
    String multipleLineCommentStarts2="*/ code/*";
    counter.onlyComment(multipleLineCommentStarts2);
    assertEquals(counter.get_fileCounter(),1);
    assertTrue(counter.get_commentOverRows());
    
    counter.set_fileCounter(0);
    counter.set_commentOverRows(true);
    String singleCommentOperator="//*/";
    counter.onlyComment(singleCommentOperator);
    assertEquals(counter.get_fileCounter(),0);
    assertFalse(counter.get_commentOverRows());
  }

}




