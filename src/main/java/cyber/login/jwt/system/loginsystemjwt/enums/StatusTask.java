package cyber.login.jwt.system.loginsystemjwt.enums;

public enum StatusTask {
        PENDENTE("pendente"),
        EMANDAMENTO("emAndamento"),
        CONCLUIDA("concluida");
    
        private String status;
    
        StatusTask (String status){
            this.status = status;
        }
    
        public String getStatus(){
            return status;
        }
    
}
