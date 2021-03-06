package com.example.mychatappnetty.entity.dto;


/**
 * @Description: Self-defined Response Type
 * 				200：Successful
 * 				500：Err, Err msg is the msg field
 * 				501：bean validation error...
 * 				502：token Error,
 * 				555：Other Exception
 */
public class JsonResult {

        // Response Code
        private Integer status;

        // Response msg
        private String msg;

        // Response data
        private Object data;

        private String ok;


        public static JsonResult build(Integer status, String msg, Object data) {
            return new JsonResult(status, msg, data);
        }

        public static JsonResult ok(Object data) {
            return new JsonResult(data);
        }

        public static JsonResult ok() {
            return new JsonResult(null);
        }

        public static JsonResult errorMsg(String msg) {
            return new JsonResult(500, msg, null);
        }

        public static JsonResult errorMap(Object data) {
            return new JsonResult(501, "error", data);
        }

        public static JsonResult errorTokenMsg(String msg) {
            return new JsonResult(502, msg, null);
        }

        public static JsonResult errorException(String msg) {
            return new JsonResult(555, msg, null);
        }

        public JsonResult() {

        }


        public JsonResult(Integer status, String msg, Object data) {
            this.status = status;
            this.msg = msg;
            this.data = data;
        }

        public JsonResult(Object data) {
            this.status = 200;
            this.msg = "OK";
            this.data = data;
        }

        public Boolean isOK() {
            return this.status == 200;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getOk() {
            return ok;
        }

        public void setOk(String ok) {
            this.ok = ok;
        }
}
