package top.chende.shuinikg.dto;

/**
 * @author: chende
 * @date: 2019/7/29 18:12
 * @description:
 */
public class ArticleSumDTO {
    Long id;
    String sum;

    public ArticleSumDTO(){}
    public ArticleSumDTO(Long id,String sum){
        this.id=id;
        this.sum=sum;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
