<tr>
    <td>
        <div class="form-group m-0">
            <input type="text" name="date"
                   value="<#if record??>${record.date}</#if>"
                   class="form-control ${(dateError??)?string('is-invalid', '')}" placeholder="Date" />
            <#if dateError??>
                <div class="invalid-feedback">
                    ${dateError}
                </div>
            </#if>
        </div>
    </td>
    <td>
        <div class="form-group m-0">
            <input type="text" class="form-control ${(temperatureError??)?string('is-invalid', '')}"
                   value="<#if record??>${record.temperature}</#if>" name="temperature" placeholder="Temperature" />
            <#if temperatureError??>
                <div class="invalid-feedback">
                    ${temperatureError}
                </div>
            </#if>
        </div>
    </td>
    <td>
        <div class="form-group m-0">
            <input type="text" class="form-control ${(windSpeedError??)?string('is-invalid', '')}"
                   value="<#if record??>${record.windSpeed}</#if>" name="windSpeed" placeholder="WindSpeed" />
            <#if windSpeedError??>
                <div class="invalid-feedback">
                    ${windSpeedError}
                </div>
            </#if>
        </div>
    </td>
    <td>
        <div class="form-group m-0">
            <input type="text" class="form-control ${(windAngleError??)?string('is-invalid', '')}"
                   value="<#if record??>${record.windAngle}</#if>" name="windAngle" placeholder="WindAngle" />
            <#if windAngleError??>
                <div class="invalid-feedback">
                    ${windAngleError}
                </div>
            </#if>
        </div>
    </td>
    <td>
        <div class="form-group m-0">
            <input type="text" class="form-control ${(pressureError??)?string('is-invalid', '')}"
                   value="<#if record??>${record.pressure}</#if>" name="pressure" placeholder="Pressure" />
            <#if pressureError??>
                <div class="invalid-feedback">
                    ${pressureError}
                </div>
            </#if>
        </div>
    </td>

    <td>
        <input type="hidden" name="id" value="<#if record??>${record.id!}</#if>" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="form-group m-0">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </td>
</tr>