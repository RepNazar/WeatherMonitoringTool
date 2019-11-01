<#include "security.ftl">
<#list page.content as record>
    <tr>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.date}</a>
        </td>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.temperature}</a>
        </td>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.windSpeed}</a>
        </td>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.windAngle}</a>
        </td>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.pressure}</a>
        </td>
        <td>
            <#if record.author.id == currentUserId>
                <form method="post" action="/user-records/${record.author.id}/delete">
                    <input type="hidden" name="id" value="${record.id}"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <div class="form-group m-0">
                        <button type = "submit" class ="btn btn-link p-0" style="line-height: normal!important;">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </div>
                </form>
            </#if>
        </td>
    </tr>
<#else>
    <tr>
        <td>No Records</td>
    </tr>
</#list>

