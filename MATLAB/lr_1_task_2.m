% Определение матрицы коэффициентов весомости
coefficients = [
    0.4  0.2  0.35 0.2;
    0.2  0.4  0.20 0.5;
    0.15 0.3  0.25 0.2;
    0.25 0.1  0.20 0.1
];

% Преобразование коэффициентов в ранги
[~, ranks] = sort(coefficients, 'descend');
ranks = reshape(ranks, size(coefficients));

% Вычисление коэффициента конкордации
m = size(coefficients, 1); % количество параметров
n = size(coefficients, 2); % количество экспертов

S = sum((sum(ranks) - n*(m+1)/2).^2);
W = 12*S / (n^2*(m^3-m));

% Вычисление критерия
chi_square = n*(m-1)*W;
df = m - 1;
p_value = 1 - chi2cdf(chi_square, df);

% Вычисление средних рангов и общего ранжирования
mean_ranks = mean(ranks, 2);
[~, final_ranks] = sort(mean_ranks);

% Вывод результатов
disp('Ранги параметров:');
disp(ranks);

disp(['Коэффициент конкордации: ', num2str(W)]);

figure;
bar(final_ranks);
title('Оценка значимости критериев');
xlabel('Критерии');
ylabel('Значимость');
xticks(1:6);
xticklabels({'Качество зерна', 'Цена зерна', 'Транспортные расходы', ...
           'Форма оплаты', 'Минимальная партия', 'Надежность поставки'})



disp('Ранжирование параметров:');
for i = 1:n
    disp(['Параметр ', num2str(final_ranks(i)), ' (среднее ', num2str(mean_ranks(final_ranks(i))), ')']);
end