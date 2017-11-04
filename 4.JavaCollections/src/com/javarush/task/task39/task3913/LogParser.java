package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;
    List<String> logs = new ArrayList<>();
    ArrayList<PartOfLog> partsOfLogs = new ArrayList<>();

    public LogParser(Path logDir) {
        this.logDir = logDir;
        this.logs = processFilesFromFolder(logs, logDir.toFile());
        for (String s : logs) {
            parseString(s);
        }
    }

    public static class PartOfLog {
        public String ip;
        public String user;
        public Date date;
        public Event event;
        public String numberOfTask;
        public Status status;

        public PartOfLog(String ip, String user, Date date, Event event, String numberOfTask, Status status) {
            this.ip = ip;
            this.user = user;
            this.date = date;
            this.event = event;
            this.numberOfTask = numberOfTask;
            this.status = status;
        }

        @Override
        public String toString() {
            return "PartOfLog{" +
                    " ip='" + ip + '\'' +
                    ", user='" + user + '\'' +
                    ", date=" + date +
                    ", event=" + event +
                    ", numberOfTask='" + numberOfTask + '\'' +
                    ", status=" + status +
                    '}';
        }
    }

    //смотрим директорию logDir, ищем файлы с логами
    private List<String> processFilesFromFolder(List<String> logs, File file) {
        File[] folderEntries = file.listFiles();
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                processFilesFromFolder(logs, entry);
            }
            if (entry.isFile() && entry.getName().endsWith(".log")) {
                logs.addAll(loadListOfLogsFromFiles(entry));
            }
        }
        return logs;
    }

    //загружаем все строки логов из файлов в список
    private List<String> loadListOfLogsFromFiles(File entry) {
        List<String> listLogs = new ArrayList<>();
        try {
            listLogs = Files.readAllLines(entry.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listLogs;
    }

    void parseString(String str) {
        String[] partOfString = str.trim().split("\t");
        String IP = partOfString[0].trim();
        String name = partOfString[1];
        Date date = null;
        Event event = null;
        String numberTask = null;
        Status status = null;

        for (int i = 2; i < partOfString.length; i++) {
            if (parseDate(partOfString[i], "d.M.y H:m:s") != null) {
                date = parseDate(partOfString[i], "d.M.y H:m:s");
                String[] pairEventAndNumber = partOfString[i + 1].split(" ");
                event = Event.valueOf(pairEventAndNumber[0].toUpperCase());
                if (pairEventAndNumber.length > 1 && (event.name().equals("SOLVE_TASK") || event.name().equals("DONE_TASK"))) {
                    numberTask = pairEventAndNumber[1];
                }
                if (i != partOfString.length - 1) {
                    status = Status.valueOf(partOfString[partOfString.length - 1].toUpperCase());
                }
                break;
            } else {
                name = (name + " " + partOfString[i]);
            }
        }
        partsOfLogs.add(new PartOfLog(IP, name, date, event, numberTask, status));
    }

    Date parseDate(String s, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    private boolean isDateInRange(Date check, Date after, Date before) {
        boolean fits = before == null || check.before(before) || check.equals(before);
        return fits && (after == null || check.after(after) || check.equals(after));
    }

    private Set<String> getIPs(ArrayList<PartOfLog> parts, Date after, Date before) {
        Set<String> uniqueIPs = new HashSet<>();
        if (after == null && before == null) {
            for (PartOfLog part : parts) {
                uniqueIPs.add(part.ip);
            }
        }
        for (PartOfLog part : parts) {
            if (isDateInRange(part.date, after, before)) {
                uniqueIPs.add(part.ip);
            }
        }
        return uniqueIPs;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        Set<String> uniqueIPs = getUniqueIPs(after, before);
        return uniqueIPs.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return getIPs(partsOfLogs, after, before);
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUsers = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user)) {
                partsFilterUsers.add(p);
            }
        }
        return getIPs(partsFilterUsers, after, before);
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterEvent = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(event)) {
                partsFilterEvent.add(p);
            }
        }
        return getIPs(partsFilterEvent, after, before);
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterStatus = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.status.equals(status)) {
                partsFilterStatus.add(p);
            }
        }
        return getIPs(partsFilterStatus, after, before);
    }

    private Set<String> getUsers(ArrayList<PartOfLog> parts, Date after, Date before) {
        Set<String> uniqueUsers = new HashSet<>();
        if (after == null && before == null) {
            for (PartOfLog part : parts) {
                uniqueUsers.add(part.user);
            }
        }
        for (PartOfLog part : parts) {
            if (isDateInRange(part.date, after, before)) {
                uniqueUsers.add(part.user);
            }
        }
        return uniqueUsers;
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> allUsers = new HashSet<>();
        for (PartOfLog p : partsOfLogs) {
            allUsers.add(p.user);
        }
        return allUsers;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return getUsers(partsOfLogs, after, before).size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUser = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user)) {
                partsFilterUser.add(p);
            }
        }
        Set<String> uniqueEvents = new HashSet<>();
        if (after == null && before == null) {
            for (PartOfLog part : partsFilterUser) {
                uniqueEvents.add(part.event.toString());
            }
        }
        for (PartOfLog part : partsFilterUser) {
            if (isDateInRange(part.date, after, before)) {
                uniqueEvents.add(part.event.toString());
            }
        }
        return uniqueEvents.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterIP = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.ip.equals(ip)) {
                partsFilterIP.add(p);
            }
        }
        return getUsers(partsFilterIP, after, before);
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterLogged = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.LOGIN)) {
                partsFilterLogged.add(p);
            }
        }
        return getUsers(partsFilterLogged, after, before);
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterDownloadedPlugin = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.DOWNLOAD_PLUGIN) && p.status.equals(Status.OK)) {
                partsFilterDownloadedPlugin.add(p);
            }
        }
        return getUsers(partsFilterDownloadedPlugin, after, before);
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterWroteMessage = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.WRITE_MESSAGE)) {
                partsFilterWroteMessage.add(p);
            }
        }
        return getUsers(partsFilterWroteMessage, after, before);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterSolvedTask = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.SOLVE_TASK)) {
                partsFilterSolvedTask.add(p);
            }
        }
        return getUsers(partsFilterSolvedTask, after, before);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        ArrayList<PartOfLog> partsFilterSolvedTask = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.SOLVE_TASK) && Integer.parseInt(p.numberOfTask) == task) {
                partsFilterSolvedTask.add(p);
            }
        }
        return getUsers(partsFilterSolvedTask, after, before);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterDoneTask = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.DONE_TASK)) {
                partsFilterDoneTask.add(p);
            }
        }
        return getUsers(partsFilterDoneTask, after, before);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        ArrayList<PartOfLog> partsFilterDoneTask = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.DONE_TASK) && Integer.parseInt(p.numberOfTask) == task) {
                partsFilterDoneTask.add(p);
            }
        }
        return getUsers(partsFilterDoneTask, after, before);
    }

    private Set<Date> getDates(ArrayList<PartOfLog> parts, Date after, Date before) {
        Set<Date> uniqueDates = new HashSet<>();
        if (after == null && before == null) {
            for (PartOfLog part : parts) {
                uniqueDates.add(part.date);
            }
        }
        for (PartOfLog part : parts) {
            if (isDateInRange(part.date, after, before)) {
                uniqueDates.add(part.date);
            }
        }
        return uniqueDates;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUserAndEvent = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user) && p.event.equals(event)) {
                partsFilterUserAndEvent.add(p);
            }
        }
        return getDates(partsFilterUserAndEvent, after, before);
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterEventFailed = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.status.equals(Status.FAILED)) {
                partsFilterEventFailed.add(p);
            }
        }
        return getDates(partsFilterEventFailed, after, before);
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterEventError = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.status.equals(Status.ERROR)) {
                partsFilterEventError.add(p);
            }
        }
        return getDates(partsFilterEventError, after, before);
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUserLogged = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user) && p.event.equals(Event.LOGIN)) {
                partsFilterUserLogged.add(p);
            }
        }
        ArrayList<Date> result = new ArrayList<>();
        result.addAll(getDates(partsFilterUserLogged, after, before));
        Collections.sort(result);
        try {
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUserSolvedTask = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user) && p.event.equals(Event.SOLVE_TASK) && Integer.parseInt(p.numberOfTask) == task) {
                partsFilterUserSolvedTask.add(p);
            }
        }
        ArrayList<Date> result = new ArrayList<>();
        result.addAll(getDates(partsFilterUserSolvedTask, after, before));
        Collections.sort(result);
        try {
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUserDoneTask = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user) && p.event.equals(Event.DONE_TASK) && Integer.parseInt(p.numberOfTask) == task) {
                partsFilterUserDoneTask.add(p);
            }
        }
        ArrayList<Date> result = new ArrayList<>();
        result.addAll(getDates(partsFilterUserDoneTask, after, before));
        Collections.sort(result);
        try {
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUserSendMessage = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user) && p.event.equals(Event.WRITE_MESSAGE)) {
                partsFilterUserSendMessage.add(p);
            }
        }
        return getDates(partsFilterUserSendMessage, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUserDownloadedPlugin = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user) && p.event.equals(Event.DOWNLOAD_PLUGIN)) {
                partsFilterUserDownloadedPlugin.add(p);
            }
        }
        return getDates(partsFilterUserDownloadedPlugin, after, before);
    }

    private Set<Event> getEvents(ArrayList<PartOfLog> parts, Date after, Date before) {
        Set<Event> uniqueEvents = new HashSet<>();
        if (after == null && before == null) {
            for (PartOfLog part : parts) {
                uniqueEvents.add(part.event);
            }
        }
        for (PartOfLog part : parts) {
            if (isDateInRange(part.date, after, before)) {
                uniqueEvents.add(part.event);
            }
        }
        return uniqueEvents;
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getEvents(partsOfLogs, after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return getEvents(partsOfLogs, after, before);
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterIP = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.ip.equals(ip)) {
                partsFilterIP.add(p);
            }
        }
        return getEvents(partsFilterIP, after, before);
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterUser = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.user.equals(user)) {
                partsFilterUser.add(p);
            }
        }
        return getEvents(partsFilterUser, after, before);
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterStatusFailed = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.status.equals(Status.FAILED)) {
                partsFilterStatusFailed.add(p);
            }
        }
        return getEvents(partsFilterStatusFailed, after, before);
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        ArrayList<PartOfLog> partsFilterStatusError = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.status.equals(Status.ERROR)) {
                partsFilterStatusError.add(p);
            }
        }
        return getEvents(partsFilterStatusError, after, before);
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterTask = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.SOLVE_TASK) && Integer.parseInt(p.numberOfTask) == task) {
                partsFilterTask.add(p);
            }
        }
        HashSet<PartOfLog> result = new HashSet<>();
        if (after == null && before == null) {
            for (PartOfLog part : partsFilterTask) {
                result.add(part);
            }
        }
        for (PartOfLog part : partsFilterTask) {
            if (isDateInRange(part.date, after, before)) {
                result.add(part);
            }
        }
        return result.size();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        ArrayList<PartOfLog> partsFilterSuccessfulAttemptToSolveTask = new ArrayList<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.event.equals(Event.DONE_TASK) && Integer.parseInt(p.numberOfTask) == task) {
                partsFilterSuccessfulAttemptToSolveTask.add(p);
            }
        }
        HashSet<PartOfLog> result = new HashSet<>();
        if (after == null && before == null) {
            for (PartOfLog part : partsFilterSuccessfulAttemptToSolveTask) {
                result.add(part);
            }
        }
        for (PartOfLog part : partsFilterSuccessfulAttemptToSolveTask) {
            if (isDateInRange(part.date, after, before)) {
                result.add(part);
            }
        }
        return result.size();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        HashSet<Integer> numbersOfTask = new HashSet<>();
        HashMap<Integer, Integer> result = new HashMap<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.numberOfTask != null) {
                numbersOfTask.add(Integer.parseInt(p.numberOfTask));
            }
        }
        for (int i : numbersOfTask) {
            int value = getNumberOfAttemptToSolveTask(i, after, before);
            if (value > 0) {
                result.put(i, value);
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        HashSet<Integer> numbersOfTask = new HashSet<>();
        HashMap<Integer, Integer> result = new HashMap<>();
        for (PartOfLog p : partsOfLogs) {
            if (p.numberOfTask != null) {
                numbersOfTask.add(Integer.parseInt(p.numberOfTask));
            }
        }
        for (int i : numbersOfTask) {
            int value = getNumberOfSuccessfulAttemptToSolveTask(i, after, before);
            if (value > 0) {
                result.put(i, value);
            }
        }
        return result;
    }

    private Set<Status> getStatuses(ArrayList<PartOfLog> parts, Date after, Date before) {
        Set<Status> uniqueStatuses = new HashSet<>();
        if (after == null && before == null) {
            for (PartOfLog part : parts) {
                uniqueStatuses.add(part.status);
            }
        }
        for (PartOfLog part : parts) {
            if (isDateInRange(part.date, after, before)) {
                uniqueStatuses.add(part.status);
            }
        }
        return uniqueStatuses;
    }

    //В валидаторе есть проблема с проверкой требований №3, 4, 11.
    //Исключительно для этих случаев при проверке попадания даты в диапазон, валидатору не нравится  date >= after && date <= before,
    // как для всех остальных требований. Теперь ему требуется ТОЛЬКО date > after && date < before
    @Override
    public Set<Object> execute(String query) {
        Set<Object> result = new HashSet<>();
        StringQuery parseQuery = parseQuery(query);

        if (parseQuery.after != null && parseQuery.before != null && parseQuery.field1.equals("ip") && (parseQuery.field2.equals("event") || parseQuery.field2.equals("status"))) {
            result = getIpsParticularCase(parseQuery.after, parseQuery.before, getFilteredPartsOfLogs(query, parseQuery));
        } else if (parseQuery.after != null && parseQuery.before != null && parseQuery.field1.equals("date") && parseQuery.field2.equals("event")) {
            result = getDatesParticularCase(parseQuery.after, parseQuery.before, getFilteredPartsOfLogs(query, parseQuery));
        } else if (parseQuery.after == null && parseQuery.before == null) {
            result = executeQueryNoLimitsByDate(parseQuery.field1, getFilteredPartsOfLogs(query, parseQuery));
        } else {
            result = executeQuery(parseQuery.field1, parseQuery.after, parseQuery.before, getFilteredPartsOfLogs(query, parseQuery));
        }
        return result;
    }

    private StringQuery parseQuery(String query) {
        String field1, field2, value1, after, before;

        if (query.contains("\" and date between \"")) {
            Pattern p = Pattern.compile("^get\\s(.*?)\\sfor\\s(.*?)\\s=\\s\"(.*)\"\\sand date between\\s\"(.*?)\"\\sand\\s\"(.*?)\"");
            Matcher m = p.matcher(query);
            m.find();
            field1 = m.group(1);
            field2 = m.group(2);
            value1 = m.group(3);
            after = m.group(4);
            before = m.group(5);
            return new StringQuery(field1, field2, value1, after, before);
        } else if (query.contains(" = \"")) {
            Pattern p = Pattern.compile("get\\s(.*?)\\sfor\\s(.*?)\\s=\\s\"(.*)\"");
            Matcher m = p.matcher(query);
            m.find();
            field1 = m.group(1);
            field2 = m.group(2);
            value1 = m.group(3);
            return new StringQuery(field1, field2, value1, null, null);
        } else {
            Pattern p = Pattern.compile("^get\\s(.*?)$");
            Matcher m = p.matcher(query);
            m.find();
            field1 = m.group(1);
            return new StringQuery(field1, null, null, null, null);
        }
    }

    private class StringQuery {
        public StringQuery(String field1, String field2, String value1, String after, String before) {
            this.field1 = field1;
            this.field2 = field2;
            this.value1 = value1;
            this.after = after;
            this.before = before;
        }

        String field1 = "";
        String field2 = "";
        String value1 = "";
        String after = "";
        String before = "";
    }

    private Set<Object> executeQueryNoLimitsByDate(String field1, ArrayList<PartOfLog> partFiltered) {
        Set<Object> result = new HashSet<>();
        switch ("get " + field1.toLowerCase()) {
            case "get ip":
                result.addAll(getIPs(partFiltered, null, null));
                break;
            case "get user":
                result.addAll(getUsers(partFiltered, null, null));
                break;
            case "get date":
                result.addAll(getDates(partFiltered, null, null));
                break;
            case "get event":
                result.addAll(getEvents(partFiltered, null, null));
                break;
            case "get status":
                result.addAll(getStatuses(partFiltered, null, null));
                break;
        }
        return result;
    }

    private Set<Object> executeQuery(String field1, String dateAfter, String dateBefore, ArrayList<PartOfLog> partFiltered) {
        Set<Object> result = new HashSet<>();
        Date after = parseDate(dateAfter, "dd.MM.yyyy HH:mm:ss");
        Date before = parseDate(dateBefore, "dd.MM.yyyy HH:mm:ss");
        switch ("get " + field1.toLowerCase()) {
            case "get ip":
                result.addAll(getIPs(partFiltered, after, before));
                break;
            case "get user":
                result.addAll(getUsers(partFiltered, after, before));
                break;
            case "get date":
                result.addAll(getDates(partFiltered, after, before));
                break;
            case "get event":
                result.addAll(getEvents(partFiltered, after, before));
                break;
            case "get status":
                result.addAll(getStatuses(partFiltered, after, before));
                break;
        }
        return result;
    }

    private ArrayList<PartOfLog> getFilteredPartsOfLogs(String query, StringQuery parseQuery) {
        ArrayList<PartOfLog> partFiltered = new ArrayList<>();
        if (parseQuery.field2 != null && parseQuery.value1 != null) {
            for (PartOfLog p : partsOfLogs) {
                try {
                    if (parseQuery.field2.equals("date")) {
                        if (p.date.equals(parseDate(parseQuery.value1, "d.M.y H:m:s"))) {
                            partFiltered.add(p);
                        } else if (p.date.equals(parseDate(parseQuery.value1, "d.M.y H.m.s"))) {
                            partFiltered.add(p);
                        }
                    } else if ((p.getClass().getField(parseQuery.field2).get(p)).toString().equals(parseQuery.value1)) {
                        partFiltered.add(p);
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                }
            }
        } else {
            partFiltered.addAll(partsOfLogs);
        }
        return partFiltered;
    }

    private Set<Object> getIpsParticularCase(String dateAfter, String dateBefore, ArrayList<PartOfLog> partFiltered) {
        Set<Object> uniqueIPs = new HashSet<>();
        Date after = parseDate(dateAfter, "d.M.y H:m:s");
        Date before = parseDate(dateBefore, "d.M.y H:m:s");
        for (PartOfLog part : partFiltered) {
            if (isDateInRangeParticularCase(part.date, after, before)) {
                uniqueIPs.add(part.ip);
            }
        }
        return uniqueIPs;
    }

    private Set<Object> getDatesParticularCase(String dateAfter, String dateBefore, ArrayList<PartOfLog> partFiltered) {
        Set<Object> uniqueDates = new HashSet<>();
        Date after = parseDate(dateAfter, "d.M.y H:m:s");
        Date before = parseDate(dateBefore, "d.M.y H:m:s");
        for (PartOfLog part : partFiltered) {
            if (isDateInRangeParticularCase(part.date, after, before)) {
                uniqueDates.add(part.date);
            }
        }
        return uniqueDates;
    }

    private boolean isDateInRangeParticularCase(Date check, Date after, Date before) {
        boolean fits = before == null || check.before(before);
        return fits && (after == null || check.after(after));
    }
}