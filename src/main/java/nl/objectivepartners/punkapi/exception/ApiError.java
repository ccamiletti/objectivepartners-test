package nl.objectivepartners.punkapi.exception;

import org.springframework.http.HttpStatus;

public class ApiError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8999366397431190281L;
	
	private HttpStatus status;
    private String message;
    private String error;
    
    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
 
    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        this.error = error;
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
    
}
