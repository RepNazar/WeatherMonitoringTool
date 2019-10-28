<#import "parts/common.ftl" as c>

<@c.page>
    <#if record??>
        <h3>Record</h3>
    </#if>
    <#if dateError??>
        <h3>DateError</h3>
    </#if>

    <h4>Records by: ${user.username}</h4>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/user-records/${user.id}" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Date starts with">
                <button type="submit" class="btn btn-primary ml-2"><i class="fas fa-search"></i></button>
            </form>
        </div>
    </div>

    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">

            <table class="table table-sm">

                <thead>
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">Temperature</th>
                    <th scope="col">WindSpeed</th>
                    <th scope="col">WindAngle</th>
                    <th scope="col">Pressure</th>
                    <th scope="col"></th>
                </tr>
                </thead>

                <tbody>

                <#if isCurrentUser>
                    <#include "parts/recordEdit.ftl" />
                </#if>

                <#include "parts/recordList.ftl" />
                </tbody>
            </table>

        </form>
    </div>
</@c.page>
