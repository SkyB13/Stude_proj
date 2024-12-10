%ЛР 9-10, задание №1
%x = (3: 0.1: 11);
%y = gaussmf(x, [1, 7]);
%plot(x, y);

%Задание №2
%x = 0 : 0.1 : 10;
%mf_1 = trimf(x, [3 5 7]);
%mf_2 = gaussmf(x, [1 6]);
%x_1 = (0 : 0.1 : 10);
%x_2 = (2 : 0.1 : 10);
%[X, Y] = meshgrid(x_1, x_2);
%Z = min(trimf(X, [3 5 7]), gaussmf(Y, [1 6]));
%plot3(X, Y, Z);

%Задание №3
x = 0 : 0.1 : 10;
mf_1 = trimf(x, [3 5 7]);
mf_2 = gaussmf(x, [1 6]);
x_1 = (0 : 0.1 : 10);
x_2 = (2 : 0.1 : 10);
[X, Y] = meshgrid(x_1, x_2);
Z = max(0.5 * mf_1, 0.5 * mf_2);
%Построение графика с результатом
figure('Tag','defuzz');
plot(x, Z, 'LineWidth', 1)
h_gca = gca;
h_gca.YTick = [0 .45 1];
ylim([-1 1]);
grid minor;

x3 = defuzz(x, Z, 'mom');
h2.Color = gray;
t2.Color = gray;
h3 = line([x3 x3], [0.2 0.6], 'Color', 'k');
t3 = text(x3, 0.2, 'mom', 'FontWeight','bold');
fprintf("Результат дефаззификации: ");
disp(x3);
