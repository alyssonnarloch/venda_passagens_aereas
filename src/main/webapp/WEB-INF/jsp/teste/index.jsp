<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="middle-content">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="widget-item">
                    <h3 class="widget-title">Latest News</h3>                    
                    
                    <c:forEach items="${schedules}" var="schedule">
	                    <div class="post-small">
	                        <div class="post-date">
	                            <span class="time">12</span>
	                            <span>June</span>
	                        </div> <!-- /.post-thumb -->
	                        <div class="post-content">
	                            <h4><a href="#">Lorem Ipsum</a></h4>
	                            <span>${schedule.startDestination.cityName} a ${schedule.endDestination.cityName}</span>
	                            <p>Nunc aliquam vestibulum veliesat, et aliquet lorem aliquet nec. Sed orsum rhoncus ipsum imperdiet accumsan laoreient.</p>
	                        </div> <!-- /.post-content -->
	                    </div> <!-- /.post-small -->
                    </c:forEach>
                    
                </div> <!-- /.widget-item -->
            </div> <!-- /.col-md-4 -->
            
        </div> <!-- /.row -->
    </div> <!-- /.container -->
</div> <!-- /.middle-content -->